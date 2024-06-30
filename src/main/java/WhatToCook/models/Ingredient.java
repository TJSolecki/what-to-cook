package WhatToCook.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Ingredient(
    @Id Integer ingredient_id,
    AggregateReference<Recipe, Integer> recipe,
    String ingredient_name) {}
