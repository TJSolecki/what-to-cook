package com.what.to.cook.structs;

import com.what.to.cook.models.Nutrition;

public record RecipeDto(
    String imageUrl,
    String name,
    String cookTime,
    String prepTime,
    String totalTime,
    String recipeUrl,
    String recipeYield,
    Nutrition nutrition
) {}
