package WhatToCook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeDecoding(
    List<String> image,
    String name,
    String description,
    String cookTime,
    String prepTime,
    String totalTime,
    String keywords,
    String mainEntityOfPage,
    NutritionIntermediate nutrition,
    List<String> recipeYield,
    List<String> recipeCategory,
    List<String> recipeCuisine,
    List<String> recipeIngredient,
    List<InstructionDecoding> recipeInstructions) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record InstructionDecoding(String text, String name) {}
