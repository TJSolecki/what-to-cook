package WhatToCook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Recipe(
    // Required property
    @NotNull List<String> image,

    // Optional properties
    String name,
    String description,
    String cookTime,
    String prepTime,
    String totalTime,
    String recipeUrl,
    String keywords,
    Nutrition nutrition,
    List<String> recipeYield,
    List<String> recipeCategory,
    List<String> recipeCuisine,
    List<String> recipeIngredient,
    List<Instruction> recipeInstructions) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record Instruction(String text, String name) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record Nutrition(
    String calories,
    String carbohydrateContent,
    String proteinContent,
    String fatContent,
    String saturatedFatContent,
    String cholesterolContent,
    String sodiumContent,
    String fiberContent,
    String sugarContent,
    String unsaturatedFatContent,
    String servingSize) {}
