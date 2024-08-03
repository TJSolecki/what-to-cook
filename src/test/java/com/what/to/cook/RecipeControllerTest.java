package com.what.to.cook;

import static org.junit.jupiter.api.Assertions.*;

import com.what.to.cook.models.Recipe;
import com.what.to.cook.structs.RecipeRequest;

import java.util.ArrayList;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeControllerTest {

    @Test
    void contentLoads() {
    }

    @Autowired
    TestRestTemplate rest;

    @Test
    void postShouldReturnOkStatus() {
        ResponseEntity<String> response = rest.postForEntity(
                "/api/recipe",
                new RecipeRequest("https://downshiftology.com/recipes/shakshuka/"),
                String.class
        );
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    void getShouldReturnOkStatus() {
        ResponseEntity<String> response = rest.getForEntity("/api/recipe", String.class);
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    void postShouldPersistRecipeToDb() {
        ResponseEntity<String> response = rest.postForEntity(
                "/api/recipe",
                new RecipeRequest("https://downshiftology.com/recipes/shakshuka/"),
                String.class
        );
        ResponseEntity<ArrayList<Recipe>> res = (ResponseEntity<ArrayList<Recipe>>) rest.getForEntity(
                "/api/recipe",
                new ArrayList<Recipe>().getClass()
        );
        assertFalse(Objects.requireNonNull(res.getBody()).isEmpty());
    }
}
