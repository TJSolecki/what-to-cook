package WhatToCook.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Instruction(
    @Id Integer instruction_id,
    AggregateReference<Recipe, Integer> recipe,
    Integer step_number,
    String instruction) {}
