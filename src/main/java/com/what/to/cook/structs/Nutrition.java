package com.what.to.cook.structs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Nutrition(
        String calories,
        String carbohydrateContent,
        String proteinContent,
        String fatContent,
        String saturatedFatContent,
        String cholesterolContent,
        String sodiumContent,
        String fiberContent,
        String sugarContent,
        String unsaturatedFatContent,
        String servingSize) {
}
