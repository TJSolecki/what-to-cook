package WhatToCook.models;

import WhatToCook.NutritionIntermediate;
import org.springframework.data.annotation.Id;

public record Nutrition(
    @Id Integer nutrition,
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
  public Nutrition(NutritionIntermediate intermediate) {
    this(
        null,
        intermediate.calories(),
        intermediate.carbohydrateContent(),
        intermediate.proteinContent(),
        intermediate.fatContent(),
        intermediate.saturatedFatContent(),
        intermediate.cholesterolContent(),
        intermediate.sodiumContent(),
        intermediate.fiberContent(),
        intermediate.sugarContent(),
        intermediate.unsaturatedFatContent(),
        intermediate.servingSize());
  }
}
