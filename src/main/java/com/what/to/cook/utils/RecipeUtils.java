package com.what.to.cook.utils;

import com.what.to.cook.Recipe;
import com.what.to.cook.structs.RecipeJson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class RecipeUtils {
    public static Recipe getRecipeFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();

        Element scriptElement = document.selectFirst("script[type=application/ld+json]");
        if (scriptElement == null) {
            throw new RuntimeException("No ld+json script tag found");
        }
        String jsonLD = scriptElement.html();

        // Parse the JSON-LD content into a Recipe object using Jackson
        List<JsonNode> jsonNodeList = parseJsonLD(jsonLD);
        ObjectMapper objectMapper = new ObjectMapper();

        for (JsonNode node : jsonNodeList) {
            if (node.isObject() && "Recipe".equals(node.path("@type").asText())) {
                return parseRecipeJson(objectMapper.treeToValue(node, RecipeJson.class));
            }
        }
        throw new RuntimeException("Recipe type object not in array");
    }

    private static Recipe parseRecipeJson(RecipeJson recipeJson) {
        return Recipe.builder()
                .imageUrl(recipeJson.image().getLast())
                .name(recipeJson.name())
                .cookTime(recipeJson.cookTime())
                .prepTime(recipeJson.prepTime())
                .totalTime(recipeJson.totalTime())
                .recipeUrl(recipeJson.mainEntityOfPage())
                .nutrition(recipeJson.nutrition())
                .instructions(
                        IntStream.range(0, recipeJson.recipeInstructions().size())
                                .mapToObj(
                                        (i) -> Recipe.Instruction.builder()
                                                .stepNumber(i + 1)
                                                .instruction(recipeJson.recipeInstructions().get(i).text())
                                                .build())
                                .collect(Collectors.toSet())
                )
                .cuisines(new HashSet<>(recipeJson.recipeCuisine()))
                .categories(new HashSet<>(recipeJson.recipeCategory()))
                .keywords(new HashSet<>(List.of(recipeJson.keywords().split(", "))))
                .build();
    }

    // Method to parse JSON-LD string as a list of JsonNode
    private static List<JsonNode> parseJsonLD(String jsonLD) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonLD);
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
