package WhatToCook;

import java.util.List;

public record RecipeIntermediate(
    String image_url,
    String name,
    String description,
    String cookTime,
    String prepTime,
    String totalTime,
    String recipeUrl,
    String keywords,
    NutritionIntermediate nutrition,
    List<String> recipeYield,
    List<String> recipeCategory,
    List<String> recipeCuisine,
    List<String> recipeIngredient,
    List<Instruction> recipeInstructions) {}

record Instruction(int step_number, String text) {}
