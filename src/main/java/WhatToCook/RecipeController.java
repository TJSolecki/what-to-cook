package WhatToCook;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe/")
public class RecipeController {
  private final RecipeRepository recipe_repository;

  public RecipeController(RecipeRepository recipe_repository) {
    this.recipe_repository = recipe_repository;
  }

  @GetMapping("/")
  List<RecipeIntermediate> get_recipes() {
    return recipe_repository.get_recipes();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  void create(@Valid @RequestBody RecipeUrl recipe_url) throws IOException {
    recipe_repository.create_recipe_from_url(recipe_url.url());
  }

  RecipeIntermediate create_recipe_from_decoding(RecipeDecoding recipe_decoding) {
    return new RecipeIntermediate(
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
}
