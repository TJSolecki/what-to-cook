package WhatToCook.models;

import org.springframework.data.annotation.Id;

public record Cuisine(@Id Integer cuisine_id, String cusine_name) {}
