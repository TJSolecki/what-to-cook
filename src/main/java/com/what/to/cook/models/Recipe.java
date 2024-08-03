package com.what.to.cook.models;

import com.what.to.cook.json.NutritionJson;
import com.what.to.cook.json.RecipeJson;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Data
@Builder
public class Recipe {

    @Id
    private Integer id;

    private String imageUrl;
    private String name;
    private String cookTime;
    private String prepTime;
    private String totalTime;
    private String recipeUrl;
    private String recipeYield;
    private AggregateReference<Nutrition, Integer> nutritionId;

    public static Recipe fromRecipeJson(RecipeJson recipeJson) {
        return builder()
            .imageUrl(recipeJson.image().getFirst())
            .recipeUrl(recipeJson.mainEntityOfPage())
            .name(recipeJson.name())
            .cookTime(recipeJson.cookTime())
            .prepTime(recipeJson.prepTime())
            .totalTime(recipeJson.totalTime())
            .recipeYield(String.join(", ", recipeJson.recipeYield()))
            .build();
    }
}
