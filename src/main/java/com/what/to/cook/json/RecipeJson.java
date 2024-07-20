package com.what.to.cook.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.what.to.cook.structs.Nutrition;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeJson(
        String imageUrl,
        String name,
        String cookTime,
        String prepTime,
        String totalTime,
        String recipeUrl,
        String keywords,
        Nutrition nutrition,
        List<String> recipeYield,
        List<String> recipeCuisines,
        List<String> recipeCategory,
        List<InstructionJson> recipeInstructions) {
}

