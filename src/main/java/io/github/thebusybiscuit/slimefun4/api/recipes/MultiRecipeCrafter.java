package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.Recipe.RecipeSearchResult;

public interface MultiRecipeCrafter {

    public @Nonnull List<RecipeType> getRecipeTypes();

    public default List<Recipe> getRecipes() {
        final List<Recipe> recipes = new ArrayList<>();
        for (final RecipeType recipeType : getRecipeTypes()) {
            if (Recipe.getRecipes().containsKey(recipeType)) {
                Recipe.getRecipes().get(recipeType);
            }
        }
        return recipes;
    }

    public default boolean canCraft(@Nonnull Recipe recipe) { return true; }

    public default @Nullable ItemStack[] attemptCraft(
        @Nonnull ItemStack[] inputs,
        boolean consumeInputs,
        boolean cache,
        int hash
    ) {
        for (final RecipeType recipeType : getRecipeTypes()) {
            final RecipeSearchResult result = Recipe.searchRecipes(recipeType, inputs, this::canCraft, consumeInputs, cache, hash);   
            if (result.recipeExists()) return result.getRecipe().getOutputs();
        }
        return null;
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
