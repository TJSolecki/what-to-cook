package WhatToCook.models;

import org.springframework.data.annotation.Id;

public record Category(@Id Integer category_id, String category_name) {}
