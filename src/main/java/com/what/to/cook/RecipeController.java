package com.what.to.cook;

import com.what.to.cook.structs.RecipeRequest;
import com.what.to.cook.utils.RecipeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/recipe")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @PostMapping("/")
    public void addRecipe(@RequestBody RecipeRequest requestBody) throws IOException {
        recipeRepository.save(RecipeUtils.getRecipeFromUrl(requestBody.url()));
    }
}
