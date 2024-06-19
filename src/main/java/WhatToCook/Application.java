package WhatToCook;

import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    RecipeMetadataExtractor extractor = new RecipeMetadataExtractor();
    Optional<Recipe> recipe = extractor.get_recipe("https://downshiftology.com/recipes/shakshuka/");
    if (recipe.isEmpty()) {
      throw new RuntimeException("There is no recipe metadata");
    }
    System.out.println(recipe.get());
    SpringApplication.run(Application.class, args);
  }
}
