package com.what.to.cook;

import com.what.to.cook.structs.Nutrition;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Builder(builderClassName = "Builder")
@Getter
@ToString
public class Recipe {
    private final String imageUrl;
    private final String name;
    private final String cookTime;
    private final String prepTime;
    private final String totalTime;
    private final String recipeUrl;
    private final String recipeYield;
    private final Nutrition nutrition;
    private final Set<String> keywords;
    private final Set<String> cuisines;
    private final Set<String> categories;
    private final Set<Instruction> instructions;

    @Getter
    @lombok.Builder
    @ToString
    public static class Instruction {
        private final int stepNumber;
        private final String instruction;
    }
}

