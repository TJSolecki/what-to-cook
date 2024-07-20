package com.what.to.cook.utils;

import com.what.to.cook.json.RecipeJson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class RecipeUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static RecipeJson getRecipeFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        Element scriptElement = document.selectFirst("script[type=application/ld+json]");
        if (scriptElement == null) {
            throw new RuntimeException("No ld+json script tag found");
        }
        String jsonLD = scriptElement.html();

        // Parse the JSON-LD content into a Recipe object using Jackson
        List<JsonNode> jsonNodeList = parseJsonLD(jsonLD);

        for (JsonNode node : jsonNodeList) {
            if (node.isObject() && "Recipe".equals(node.path("@type").asText())) {
                return (MAPPER.treeToValue(node, RecipeJson.class));
            }
        }
        throw new RuntimeException("Recipe type object not in array");
    }

    // Method to parse JSON-LD string as a list of JsonNode
    private static List<JsonNode> parseJsonLD(String jsonLD) throws IOException {
        JsonNode rootNode = MAPPER.readTree(jsonLD);
        JsonNode graphNode = rootNode.get("@graph");
        if (graphNode == null) {
            throw new RuntimeException("no @graph field in response");
        }

        List<JsonNode> nodeList = new ArrayList<>();
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
