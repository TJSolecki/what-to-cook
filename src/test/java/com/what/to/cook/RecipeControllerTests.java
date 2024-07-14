package com.what.to.cook;

import com.what.to.cook.structs.RecipeRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecipeControllerTests {
    @Autowired
    private RecipeController recipeController;

    @Autowired
    private RecipeRepository recipeRepository;

    /**
     * Tests the {@link RecipeController#addRecipe(RecipeRequest)} function
     */
    @Test
    public void addRecipeTest() throws IOException {
        recipeController.addRecipe(new RecipeRequest("https://downshiftology.com/recipes/shakshuka/"));
        assertEquals(recipeRepository.getRecipes().size(), 1);
    }
}
