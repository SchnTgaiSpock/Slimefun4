package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import com.google.common.base.Predicate;

import io.github.thebusybiscuit.slimefun4.api.recipes.inputs.RecipeInputs;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.RecipeOutput;

/**
 * Holds all item recipes. 
 * 
 * It is the responsibilty of the implementation to
 * ensure there are no ambiguous recipes
 * 
 * @author SchnTgaiSpock
 */
public class Recipe {

    public static class RecipeSearchResult {
        private final @Nullable Recipe recipe;
        private final boolean canCraft;
        private final boolean recipeExists;
    
        public RecipeSearchResult(Recipe recipe, boolean canCraft, boolean recipeExists) {
            if (recipe == null && recipeExists) {
                throw new IllegalArgumentException("Recipe must be non-null when recipeExists is true");
            }

            this.recipe = recipe;
            this.canCraft = canCraft;
            this.recipeExists = recipeExists;
        }
    
        /**
         * @return The recipe that was matched, or null if none were matched.
         * If {@code recipeExists()} returns true, this will be non-null
         */
        @Nullable
        public Recipe getRecipe() {
            return recipe;
        }
    
        /**
         * @return Whether or not the recipe could be crafted.
         * <ul>
         * <li><b>IMPORTANT:</b> If this returns false, the return value
         * of {@code recipeExists} might return false even if a recipe
         * exists.
         */
        public boolean canCraft() {
            return canCraft;
        }
    
        /**
         * @return Whether or not the recipe exists on the RecipeType searched.
         * If true, {@code getRecipe} will be non-null
         * <ul>
         * <li><b>IMPORTANT:</b> If {@code canCraft()} returns false, this function
         * might return false even if a recipe exists.
         */
        public boolean recipeExists() {
            return recipeExists;
        }
    }

    private static final Map<RecipeType, List<Recipe>> recipes = new HashMap<>();
    private static final int CACHE_SIZE = 50;
    private static final Map<Integer, Recipe> recentlyUsed = new LinkedHashMap<>(CACHE_SIZE, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<Integer, Recipe> eldest) {
            return size() >= CACHE_SIZE;
        };
    }; // TODO: Only cache when can craft more than 1 of the recipe

    final RecipeInputs inputs;
    final RecipeOutput outputs;

    public Recipe(RecipeInputs inputs, RecipeOutput outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public boolean matches(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        return this.inputs.matches(inputs, consumeInputs);
    }

    public ItemStack[] getOutputs() {
        return outputs.getOutputs();
    }

    public ItemStack[] getGuideRecipe() {
        return inputs.getGuideRecipe();
    }

    public List<ItemStack> getGuideBottomRows() {
        return inputs.getGuideBottomRows();
    }

    public List<ItemStack> getAsBottomRowRecipe() {
        if (inputs.isSingleItem() && outputs.isSingleItem()) {
            return List.of(inputs.getGuideRecipe()[0], outputs.getOutputs()[0]);
        } else {
            return Collections.emptyList();
        }
    }

    public static Map<RecipeType, List<Recipe>> getRecipes() {
        return Collections.unmodifiableMap(recipes);
    }

    public static Map<Integer, Recipe> getRecentlyused() {
        return Collections.unmodifiableMap(recentlyUsed);
    }

    /**
     * Searches recipes for a given input array
     * @param type The type of recipe to search for
     * @param inputs The inputs that are being searched on
     * @param canCraft A predicate taking a recipe, and returning if it
     * is able to be crafted. This should be used when extending {@code Recipe}
     * and imposing new resources
     * @param consumeInputs Whether or not to consume the inputs if a
     * valid recipe was found and is able to be crafted
     * @param cache Save the recipe to the LRU cache if the recipe can be crafted
     * multiple times from the given inputs
     * @param hash The hash of the inputs to be used when cacheing
     * @return The result of the search
     */
    @ParametersAreNonnullByDefault
    public static @Nonnull RecipeSearchResult searchRecipes(
        RecipeType type,
        ItemStack[] inputs,
        @Nullable Predicate<Recipe> canCraft,
        boolean consumeInputs,
        boolean cache,
        int hash
    ) {
        if (recentlyUsed.containsKey(hash)) {
            final Recipe recipe = recentlyUsed.get(hash);
            return new RecipeSearchResult(recipe, canCraft != null && canCraft.test(recipe), true);
        }

        final boolean canOnlyCraft1 = Arrays.stream(inputs).anyMatch(s -> s.getAmount() == 1);

        for (final Recipe recipe : recipes.get(type)) {
            final boolean craftable = canCraft != null && canCraft.test(recipe);
            if (!craftable) {
                continue;
            }
            if (recipe.matches(inputs, consumeInputs)) {
                if (cache && !canOnlyCraft1) {
                    recentlyUsed.put(hash, recipe);
                }
                return new RecipeSearchResult(recipe, true, true);
            }
        }

        return new RecipeSearchResult(null, false, false);
    }

    public static @Nonnull List<Recipe> getRecipes(RecipeType recipeType) {
        return Collections.unmodifiableList(recipes.getOrDefault(recipeType, Collections.emptyList()));
    }

    @ParametersAreNonnullByDefault
    public static @Nonnull RecipeSearchResult searchRecipes(
        RecipeType type,
        ItemStack[] inputs,
        @Nullable Predicate<Recipe> canCraft,
        boolean consumeInputs
    ) {
        int hash = 1;
        for (ItemStack item : inputs) {
            hash = hash * 31 + (item == null ? 0 : item.hashCode());
        }

        return searchRecipes(type, inputs, canCraft, consumeInputs, true, hash);
    }

    @ParametersAreNonnullByDefault
    public static @Nonnull RecipeSearchResult searchRecipes(
        RecipeType type, 
        ItemStack[] inputs,
        @Nullable Predicate<Recipe> canCraft
    ) {
        return searchRecipes(type, inputs, canCraft, true);
    }

    @ParametersAreNonnullByDefault
    public static @Nonnull RecipeSearchResult searchRecipes(
        RecipeType type, 
        ItemStack[] inputs
    ) {
        return searchRecipes(type, inputs, null);
    }

    public static void registerRecipes(RecipeType recipeType, Recipe... recipes) {
        for (final Recipe recipe : recipes) {
            if (Recipe.recipes.containsKey(recipeType)) {
                Recipe.recipes.get(recipeType).add(recipe);
            } else {
                final List<Recipe> newList = new ArrayList<>();
                newList.add(recipe);
                Recipe.recipes.put(recipeType, newList);
            }
        }
    }

    public static void registerRecipe(RecipeType recipeType, ItemStack[] inputs, ItemStack[] outputs) {
        final Recipe recipe = new RecipeBuilder().inputs(inputs).outputs(outputs).build();
        if (Recipe.recipes.containsKey(recipeType)) {
            Recipe.recipes.get(recipeType).add(recipe);
        } else {
            final List<Recipe> newList = new ArrayList<>();
            newList.add(recipe);
            Recipe.recipes.put(recipeType, newList);
        }
    }

    public static void registerRecipe(RecipeType recipeType, ItemStack input, ItemStack output) {
        registerRecipe(recipeType, new ItemStack[] { input }, new ItemStack[] { output });
    }

}
