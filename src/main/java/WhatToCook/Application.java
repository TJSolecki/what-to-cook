package WhatToCook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    RecipeMetadataExtractor extractor = new RecipeMetadataExtractor();
    Recipe recipe;
    try {
      recipe = extractor.get_recipe("https://downshiftology.com/recipes/shakshuka/");
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    System.out.println(recipe);
    SpringApplication.run(Application.class, args);
  }
}
