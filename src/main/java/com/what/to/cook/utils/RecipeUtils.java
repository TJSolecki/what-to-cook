package com.what.to.cook.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.what.to.cook.json.RecipeJson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Slf4j
public final class RecipeUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static RecipeJson getRecipeFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        Element scriptElement = document.selectFirst("script[type=application/ld+json]");
        if (scriptElement == null) {
            throw new RuntimeException("No ld+json script tag found");
        }
        String jsonLd = scriptElement.html();

        // Parse the JSON-LD content into a Recipe object using Jackson
        List<JsonNode> jsonNodeList = parseJsonLD(jsonLd);

        log.info(jsonNodeList.toString());
        for (JsonNode node : jsonNodeList) {
            log.info(node.toString());
            if (
                node.isObject() &&
                ("Recipe".equals(node.path("@type").asText()) ||
                    (node.path("@type").isArray() && "Recipe".equals(node.path("@type").get(0).asText())))
            ) {
                return (MAPPER.treeToValue(node, RecipeJson.class));
            }
        }
        throw new RuntimeException("Recipe type object not in array");
    }

    // Method to parse JSON-LD string as a list of JsonNode
    private static List<JsonNode> parseJsonLD(String jsonLD) throws IOException {
        JsonNode rootNode = MAPPER.readTree(jsonLD);
        JsonNode graphNode = rootNode.get("@graph");
        List<JsonNode> nodeList = new ArrayList<>();
        if (graphNode == null) {
            try {
                return MAPPER.readValue(jsonLD, new TypeReference<>() {});
            } catch (Exception e) {
                throw new RuntimeException("no @graph field in response");
            }
        }

        if (graphNode.isArray()) {
            // If the root node is an array, add all elements to the list
            graphNode.forEach(nodeList::add);
        } else {
            // If the root node is an object, wrap it in a list
            nodeList.add(graphNode);
        }
        return nodeList;
    }
}
