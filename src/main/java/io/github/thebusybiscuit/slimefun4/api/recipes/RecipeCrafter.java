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
        @Nonnull ItemStack[] inputs,
        boolean consumeInputs,
        boolean cache,
        int hash
    ) {
        final RecipeSearchResult result = Recipe.searchRecipes(getRecipeType(), inputs, this::canCraft, consumeInputs, cache, hash);
        return result.recipeExists() ? result.getRecipe().getOutputs() : null;
    }

    public default @Nullable ItemStack[] attemptCraft(
        @Nonnull ItemStack[] inputs,
        boolean consumeInputs,
        int hash
    ) {
        return attemptCraft(inputs, consumeInputs, true, hash);
    }
    
    public default @Nullable ItemStack[] attemptCraft(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        int hash = 1;
        for (ItemStack item : inputs) {
            hash = hash * 31 + (item == null ? 0 : item.hashCode());
        }

        return attemptCraft(inputs, consumeInputs, hash);
    }

    public default @Nullable ItemStack[] attemptCraft(@Nonnull ItemStack[] inputs) {
        return attemptCraft(inputs, true);
    }
    
}
