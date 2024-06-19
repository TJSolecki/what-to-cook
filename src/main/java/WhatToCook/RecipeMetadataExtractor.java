package WhatToCook;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.util.JSONPObject;
import java.util.Optional;
// import org.apache.tomcat.util.json.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RecipeMetadataExtractor {
  public Optional<Recipe> get_recipe(String url) {
    Document document;
    try {
      document = Jsoup.connect(url).get();
    } catch (Exception e) {
      System.out.println(e);
      return Optional.empty();
    }
    Element scriptElement = document.selectFirst("script[type=application/ld+json]");
    if (scriptElement == null) {
      return Optional.empty();
    }
    String jsonLD = scriptElement.html();
    System.out.println(jsonLD);

    // Parse the JSON-LD content into a Recipe object using Jackson
    ObjectMapper objectMapper = new ObjectMapper();
    // JSONParser parser = new JSONParser();
    // List<JSONPObject> obj = parser.parseArray(jsonLD);

    Recipe recipe;
    try {
      recipe = objectMapper.readValue(jsonLD, Recipe.class);
    } catch (Exception e) {
      System.out.println(e);
      return Optional.empty();
    }

    return Optional.of(recipe);
  }
}
