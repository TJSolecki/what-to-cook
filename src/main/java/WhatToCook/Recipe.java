package WhatToCook;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record Recipe(
    // Required property
    @NotNull String image,

    // Optional properties
    String name,
    String description,
    String cookTime,
    String prepTime,
    String totalTime,
    String recipeUrl,
    String keywords,
    List<String> recipeCategory,
    List<String> recipeCuisine,
    List<String> recipeIngredient,
    List<Instruction> instructions) {}

record Instruction(String text, String name) {}
