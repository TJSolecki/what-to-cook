package WhatToCook.models;

import WhatToCook.RecipeIntermediate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Recipe(
    @Id Integer id,
    String image_url,
    String name,
    String description,
    String cook_time,
    String prep_time,
    String total_time,
    String recipe_url,
    String recipe_yield,
    Set<Instruction> instructions,
    Set<Keyword> keywords,
    Set<RecipeCuisine> cuisines,
    Set<RecipeCategory> categories,
    AggregateReference<Nutrition, Integer> nutrition_id) {
  public Recipe(
      RecipeIntermediate recipeIntermediate, AggregateReference<Nutrition, Integer> nutrition_id) {
    this(
        null,
        recipeIntermediate.image_url(),
        recipeIntermediate.name(),
        recipeIntermediate.description(),
        recipeIntermediate.cookTime(),
        recipeIntermediate.prepTime(),
        recipeIntermediate.totalTime(),
        recipeIntermediate.recipeUrl(),
        String.join(", ", recipeIntermediate.recipeYield()),
        new HashSet<Instruction>(),
        new HashSet<Keyword>(),
        new HashSet<RecipeCuisine>(),
        new HashSet<RecipeCategory>(),
        nutrition_id);
  }

  public void addCuisine(String cuisine_name) {
    Cuisine cuisine = new Cuisine(null, cuisine_name);
    this.cuisines()
        .add(
            new RecipeCuisine(
                AggregateReference.to(this.id()), AggregateReference.to(cuisine.cuisine())));
  }

  public void addCategory(String category_name) {
    Category category = new Category(null, category_name);
    this.categories()
        .add(
            new RecipeCategory(
                AggregateReference.to(this.id), AggregateReference.to(category.category())));
  }

  public void addKeyword(String keyword_name) {
    this.keywords().add(new Keyword(null, AggregateReference.to(this.id), keyword_name));
  }

  public void addInstruction(int step_number, String instruction) {
    this.instructions()
        .add(new Instruction(null, AggregateReference.to(this.id), step_number, instruction));
  }
}
