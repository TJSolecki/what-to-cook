<script lang="ts">
    import type { Recipe } from "../../types/types";
    import RecipePreview from "./RecipePreview.svelte";

    const response: Promise<Recipe[]> = fetch("/api/recipe").then((res) =>
        res.json(),
    );
</script>

{#await response}
    <div>waiting</div>
{:then recipes}
    <ul class="grid grid-cols-1">
        {#each recipes as recipe, i (recipe.id)}
            {#if i != 0}
                <div class="width-full border-b" />
            {/if}
            <RecipePreview
                name={recipe.name}
                image={recipe.imageUrl}
                author={recipe.author}
                totalTime={recipe.totalTime}
            />
        {/each}
    </ul>
{:catch e}
    <p>{e.message}</p>
{/await}
