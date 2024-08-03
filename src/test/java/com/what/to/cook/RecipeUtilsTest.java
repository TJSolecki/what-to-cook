package com.what.to.cook;

import com.what.to.cook.utils.RecipeUtils;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecipeUtilsTest {

    @Test
    public void getRecipeFromUrlShouldNotThrowUrlWithGraphLd() throws IOException {
        RecipeUtils.getRecipeFromUrl("https://downshiftology.com/recipes/shakshuka/");
    }

    @Test
    public void getRecipeFromUrlShouldNotThrowOnUrlWithArrayLd() throws IOException {
        RecipeUtils.getRecipeFromUrl("https://www.seriouseats.com/food-lab-no-boil-baked-ziti-recipe");
    }
}
