package com.what.to.cook.structs;

import com.what.to.cook.models.Nutrition;
import com.what.to.cook.models.Recipe;

public record RecipeDto(
    String imageUrl,
    String name,
    String cookTime,
    String prepTime,
    String totalTime,
    String recipeUrl,
    String recipeYield,
    Nutrition nutrition
) {
    public RecipeDto(Recipe recipe, Nutrition nutrition) {
        this(
            recipe.getImageUrl(),
            recipe.getName(),
            recipe.getCookTime(),
            recipe.getPrepTime(),
            recipe.getTotalTime(),
            recipe.getRecipeUrl(),
            recipe.getRecipeYield(),
            nutrition
        );
    }
}
