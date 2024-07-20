package com.what.to.cook;

import com.what.to.cook.models.Recipe;
import com.what.to.cook.structs.RecipeRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.what.to.cook.utils.RecipeUtils.getRecipeFromUrl;

@AllArgsConstructor
@RequestMapping(path = "/recipe")
@RestController
public class RecipeController {
    @Autowired
    private final RecipeRepository recipeRepository;

    @PostMapping("")
    public void addRecipe(@RequestBody RecipeRequest requestBody) throws IOException {
        Recipe recipe = Recipe.fromRecipeJson(getRecipeFromUrl(requestBody.url()));
        recipeRepository.save(recipe);
    }

    @GetMapping("")
    public List<Recipe> getRecipes() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false).toList();
    }
}
