package com.what.to.cook.structs;

import jakarta.annotation.Nullable;

public record RecipeRequest(String url, @Nullable Integer cookbookId) {
    public RecipeRequest(String url) {
        this(url, null);
    }
}
