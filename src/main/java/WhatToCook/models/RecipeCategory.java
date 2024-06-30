package WhatToCook.models;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("recipe_category")
public record RecipeCategory(
    AggregateReference<Recipe, Integer> recipe, AggregateReference<Category, Integer> category) {}
