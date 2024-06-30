package WhatToCook.models;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("recipe_cusine")
public record RecipeCuisine(
    AggregateReference<Recipe, Integer> recipe, AggregateReference<Cuisine, Integer> cuisine) {}
