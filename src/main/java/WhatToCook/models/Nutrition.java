package WhatToCook.models;

import org.springframework.data.annotation.Id;

public record Nutrition(
    @Id Integer nutrition,
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
