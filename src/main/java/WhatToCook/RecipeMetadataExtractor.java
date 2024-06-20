package WhatToCook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RecipeMetadataExtractor {
  public Recipe get_recipe(String url) throws IOException {
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
      System.out.println(node);
      System.out.println("\n");
      if (node.isObject() && "Recipe".equals(node.path("@type").asText())) {
        return objectMapper.treeToValue(node, Recipe.class);
      }
    }
    throw new RuntimeException("Recipe type object not in array");
  }

  // Method to parse JSON-LD string as a list of JsonNode
  private static List<JsonNode> parseJsonLD(String jsonLD) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(jsonLD);
    JsonNode graphNode = rootNode.get("@graph");
    if (graphNode == null) {
      throw new RuntimeException("no @graph field in response");
    }

    List<JsonNode> nodeList = new ArrayList<JsonNode>();
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
