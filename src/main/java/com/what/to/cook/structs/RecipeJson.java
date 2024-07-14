package com.what.to.cook.structs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeJson(
        List<String> image,
        String name,
        String description,
        String cookTime,
        String prepTime,
        String totalTime,
        String keywords,
        String mainEntityOfPage,
        Nutrition nutrition,
        List<String> recipeYield,
        List<String> recipeCategory,
        List<String> recipeCuisine,
        List<String> recipeIngredient,
        List<InstructionJson> recipeInstructions) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record InstructionJson(String text, String name) {
    }
}

