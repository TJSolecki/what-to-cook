package com.what.to.cook;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeRepository {
    private static List<Recipe> recipes = new ArrayList<>();

    public void save(Recipe recipe) {
        recipes.add(recipe);
    }

    public static List<Recipe> getRecipes() {
        return recipes;
    }
}
