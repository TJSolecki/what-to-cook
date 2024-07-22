package com.what.to.cook.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeJson(
        String name,
        String cookTime,
        String prepTime,
        String totalTime,
        String mainEntityOfPage,
        String keywords,
        NutritionJson nutrition,
        List<String> image,
        List<String> recipeYield,
        List<String> recipeCuisines,
        List<String> recipeCategory,
        List<InstructionJson> recipeInstructions) {
}

