package WhatToCook.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Keyword(
    @Id Integer keyword_id, AggregateReference<Recipe, Integer> recipe, String keyword_name) {}
