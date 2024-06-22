package WhatToCook;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
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
  List<Recipe> get_recipes() {
    return recipe_repository.get_recipes();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  void create(@Valid @RequestBody RecipeUrl recipe_url) throws IOException {
    recipe_repository.create_recipe_from_url(recipe_url.url());
  }
}
