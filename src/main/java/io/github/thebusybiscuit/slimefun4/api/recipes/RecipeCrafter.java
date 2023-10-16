package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.Recipe.RecipeSearchResult;

public interface RecipeCrafter {

    public @Nonnull RecipeType getRecipeType();

    public default List<Recipe> getRecipes() {
        return Recipe.getRecipes().containsKey(getRecipeType()) 
            ? Recipe.getRecipes().get(getRecipeType()) 
            : Collections.emptyList();
    }

    public default boolean canCraft(@Nonnull Recipe recipe) { return true; }

    public default @Nullable ItemStack[] attemptCraft(
        @Nonnull ItemStack[] ingredients,
        boolean consumeIngredients,
        boolean cache,
        int hash
    ) {
        final RecipeSearchResult result = Recipe.searchRecipes(getRecipeType(), ingredients, this::canCraft, consumeIngredients, cache, hash);
        return result.recipeExists() ? result.getRecipe().getOutputs() : null;
    }

    public default @Nullable ItemStack[] attemptCraft(
        @Nonnull ItemStack[] ingredients,
        boolean consumeIngredients,
        int hash
    ) {
        return attemptCraft(ingredients, consumeIngredients, true, hash);
    }
    
    public default @Nullable ItemStack[] attemptCraft(@Nonnull ItemStack[] ingredients, boolean consumeIngredients) {
        int hash = 1;
        for (ItemStack item : ingredients) {
            hash = hash * 31 + (item == null ? 0 : item.hashCode());
        }

        return attemptCraft(ingredients, consumeIngredients, hash);
    }

    public default @Nullable ItemStack[] attemptCraft(@Nonnull ItemStack[] ingredients) {
        return attemptCraft(ingredients, true);
    }
    
}
