package com.what.to.cook.models;

import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Instruction(
    @Id Integer id,

    AggregateReference<Recipe, Integer> recipeId,
    Integer stepNumber,
    @Nullable String name,
    String text
) {}
