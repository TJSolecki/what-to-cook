package com.what.to.cook.structs;

import com.what.to.cook.models.Instruction;
import com.what.to.cook.models.Nutrition;
import com.what.to.cook.models.Recipe;
import jakarta.annotation.Nullable;
import java.util.List;

public record RecipeDto(
    Integer id,
    String imageUrl,
    String name,
    String author,
    String cookTime,
    String prepTime,
    String totalTime,
    String recipeUrl,
    String recipeYield,
    @Nullable Nutrition nutrition,
    List<Instruction> instructions
) {
    public RecipeDto(Recipe recipe, @Nullable Nutrition nutrition, List<Instruction> instructions) {
        this(
            recipe.getId(),
            recipe.getImageUrl(),
            recipe.getName(),
            recipe.getAuthor(),
            recipe.getCookTime(),
            recipe.getPrepTime(),
            recipe.getTotalTime(),
            recipe.getRecipeUrl(),
            recipe.getRecipeYield(),
            nutrition,
            instructions
        );
    }
}
