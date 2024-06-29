package WhatToCook.models;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("recipe_cuisine")
public record RecipeCusine(
    AggregateReference<Recipe, Integer> recipe_id,
    AggregateReference<Cuisine, Integer> cuisine_id) {}
