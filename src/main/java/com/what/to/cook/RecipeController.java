package com.what.to.cook;

import static com.what.to.cook.utils.RecipeUtils.getRecipeFromUrl;

import com.what.to.cook.json.InstructionJson;
import com.what.to.cook.json.RecipeJson;
import com.what.to.cook.models.Instruction;
import com.what.to.cook.models.Nutrition;
import com.what.to.cook.models.Recipe;
import com.what.to.cook.models.Session;
import com.what.to.cook.repositories.InstructionRepository;
import com.what.to.cook.repositories.NutritionRepository;
import com.what.to.cook.repositories.RecipeRepository;
import com.what.to.cook.repositories.SessionRepository;
import com.what.to.cook.structs.RecipeDto;
import com.what.to.cook.structs.RecipeRequest;
import com.what.to.cook.utils.AuthUtils;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(path = "/api/recipe")
@RestController
public class RecipeController {

    @Autowired
    private final RecipeRepository recipeRepository;

    @Autowired
    private final NutritionRepository nutritionRepository;

    @Autowired
    private final InstructionRepository instructionRepository;

    @Autowired
    private final SessionRepository sessionRepository;

    @PostMapping
    public void addRecipe(@RequestBody RecipeRequest requestBody) throws IOException {
        RecipeJson recipeJson = getRecipeFromUrl(requestBody.url());
        Recipe recipe = Recipe.fromRecipeJson(recipeJson);
        Nutrition nutrition = Nutrition.fromJson(recipeJson.nutrition());
        if (nutrition != null) {
            nutritionRepository.save(nutrition);
            recipe.setNutritionId(AggregateReference.to(nutrition.getId()));
        }
        recipeRepository.save(recipe);
        for (int i = 1; i < recipeJson.recipeInstructions().size() + 1; i++) {
            InstructionJson instructionJson = recipeJson.recipeInstructions().get(i - 1);
            Instruction instruction = new Instruction(
                null,
                AggregateReference.to(recipe.getId()),
                i,
                instructionJson.name(),
                instructionJson.text()
            );
            instructionRepository.save(instruction);
        }
    }

    @GetMapping("/{userId}")
    public List<RecipeDto> getRecipes(
        @Nullable @PathVariable Integer userId,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String sessionToken = AuthUtils.getSessionToken(request, response).orElse(null);
        if (sessionToken == null && userId == null) {
            return null;
        }

        Session session = sessionRepository.findBySessionToken(sessionToken).orElse(null);
        if (session == null && userId == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
        userId = userId != null ? userId : session.userId().getId();

        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
            .map(recipe -> {
                Nutrition nutrition = null;
                if (recipe.getNutritionId() != null) {
                    nutrition = nutritionRepository
                        .findById(Objects.requireNonNull(recipe.getNutritionId().getId()))
                        .orElse(null);
                }

                return new RecipeDto(
                    recipe,
                    nutrition,
                    instructionRepository.findByRecipeId(AggregateReference.to(recipe.getId()))
                );
            })
            .collect(Collectors.toList());
    }
}
