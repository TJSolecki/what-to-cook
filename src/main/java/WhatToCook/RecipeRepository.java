package WhatToCook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {
  private RecipeMetadataExtractor extractor = new RecipeMetadataExtractor();

  List<Recipe> recipes;

  public RecipeRepository() {
    recipes = new ArrayList<Recipe>();
  }

  List<Recipe> get_recipes() {
    return recipes;
  }

  Recipe create_recipe_from_decoding(RecipeDecoding recipe_decoding) {
    return new Recipe(
        recipe_decoding.image().get(0),
        recipe_decoding.name(),
        recipe_decoding.description(),
        recipe_decoding.cookTime(),
        recipe_decoding.prepTime(),
        recipe_decoding.totalTime(),
        recipe_decoding.mainEntityOfPage(),
        recipe_decoding.keywords(),
        recipe_decoding.nutrition(),
        recipe_decoding.recipeYield(),
        recipe_decoding.recipeCategory(),
        recipe_decoding.recipeCuisine(),
        recipe_decoding.recipeIngredient(),
        IntStream.range(0, recipe_decoding.recipeInstructions().size())
            .mapToObj(
                (i) -> {
                  return new Instruction(i + 1, recipe_decoding.recipeInstructions().get(i).text());
                })
            .collect(Collectors.toList()));
  }

  void create_recipe_from_url(String url) throws IOException {
    RecipeDecoding recipe_decoding = extractor.get_recipe(url);
    recipes.add(create_recipe_from_decoding(recipe_decoding));
  }
}
