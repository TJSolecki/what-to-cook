package WhatToCook.models;

import org.springframework.data.annotation.Id;

public record Category(@Id Integer category, String category_name) {}
