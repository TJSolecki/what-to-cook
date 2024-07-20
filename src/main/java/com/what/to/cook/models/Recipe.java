package com.what.to.cook.models;

import com.what.to.cook.json.RecipeJson;
import lombok.Data;
import lombok.Builder;
import org.springframework.data.annotation.Id;

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

    public static Recipe fromRecipeJson(RecipeJson recipeJson) {
        return builder().imageUrl(recipeJson.imageUrl())
                .recipeUrl(recipeJson.recipeUrl())
                .name(recipeJson.name())
                .cookTime(recipeJson.cookTime())
                .prepTime(recipeJson.prepTime())
                .totalTime(recipeJson.totalTime())
                .recipeYield(String.join(", ", recipeJson.recipeYield()))
                .build();
    }
}
