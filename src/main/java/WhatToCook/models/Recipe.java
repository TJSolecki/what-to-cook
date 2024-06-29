package WhatToCook.models;

import java.util.List;
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
    List<Instruction> instructions,
    Set<Keyword> keywords,
    Set<RecipeCuisine> cuisines,
    Set<RecipeCategory> categories,
    AggregateReference<Nutrition, Integer> nutrition_id) {
  void addCusine(Cuisine cuisine) {
    this.cuisines()
        .add(
            new RecipeCuisine(
                AggregateReference.to(this.id()), AggregateReference.to(cuisine.cuisine_id())));
  }
}
