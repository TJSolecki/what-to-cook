package com.what.to.cook;

import com.what.to.cook.utils.RecipeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest()
public class RecipeUtilsTest {

    @Test
    public void getRecipeFromUrlShouldNotThrowOnValidUrl() throws IOException {
        RecipeUtils.getRecipeFromUrl("https://downshiftology.com/recipes/shakshuka/");
    }
}
