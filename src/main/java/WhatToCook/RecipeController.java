package WhatToCook;

import WhatToCook.models.*;
import WhatToCook.repositories.NutritionRepository;
import WhatToCook.repositories.RecipeRepository;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe/")
public class RecipeController {
  private RecipeRepository recipeRepository;
  private NutritionRepository nutritionRepository;
  private final RecipeMetadataExtractor extractor;

  public RecipeController(
      RecipeRepository recipeRepository,
      RecipeMetadataExtractor recipeMetadataExtractor,
      NutritionRepository nutritionRepository) {
    this.extractor = recipeMetadataExtractor;
    this.recipeRepository = recipeRepository;
    this.nutritionRepository = nutritionRepository;
  }

  @GetMapping("/")
  List<Recipe> get_recipes() {
    return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  void create(@Valid @RequestBody RecipeUrl recipe_url) throws IOException {
    RecipeDecoding decoding = extractor.get_recipe(recipe_url.url());
    RecipeIntermediate recipeIntermediate = create_recipe_from_decoding(decoding);

    Nutrition nutrition = new Nutrition(recipeIntermediate.nutrition());
    nutrition = nutritionRepository.save(nutrition);

    Recipe recipe = new Recipe(recipeIntermediate, AggregateReference.to(nutrition.nutrition()));
    for (String keyword : recipeIntermediate.keywords().split(", ")) {
      recipe.addKeyword(keyword);
    }
    for (String cuisine_name : recipeIntermediate.recipeCuisine()) {
      recipe.addCuisine(cuisine_name);
    }
    for (String category_name : recipeIntermediate.recipeCategory()) {
      recipe.addCategory(category_name);
    }
    for (Instruction instruction : recipeIntermediate.recipeInstructions()) {
      recipe.addInstruction(instruction.step_number(), instruction.text());
    }

    recipeRepository.save(recipe);
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
