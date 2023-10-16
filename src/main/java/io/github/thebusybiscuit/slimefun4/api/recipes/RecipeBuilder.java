package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.components.GroupRecipeComponent;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.RecipeComponent;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.SingleRecipeComponent;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.TagRecipeComponent;
import io.github.thebusybiscuit.slimefun4.api.recipes.inputs.CraftingGrid;
import io.github.thebusybiscuit.slimefun4.api.recipes.inputs.RecipeIngredients;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.ItemRecipeOutput;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.RecipeOutput;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.WeightedRecipeOutput;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

public class RecipeBuilder {

    private RecipeType type = RecipeType.ENHANCED_CRAFTING_TABLE;
    private RecipeComponent<?>[] ingredients = new RecipeComponent[9];
    private RecipeShape shape = RecipeShape.TRANSLATED;
    private RecipeOutput output = RecipeOutput.EMPTY;
    private BiFunction<RecipeComponent<?>[], RecipeShape, RecipeIngredients> ingredientsProducer = CraftingGrid::new;

    public RecipeBuilder() {
    }

    public RecipeBuilder type(RecipeType type) {
        this.type = type;
        return this;
    }

    public RecipeBuilder shape(RecipeShape shape) {
        this.shape = shape;
        return this;
    }

    @SuppressWarnings("unchecked")
    public RecipeBuilder ingredients(Object... ingredients) {
        this.ingredients = new RecipeComponent<?>[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            final Object ingredient = ingredients[i];
            if (ingredient instanceof final ItemStack item) {
                this.ingredients[i] = new SingleRecipeComponent(item);
            } else if (ingredient instanceof final Material mat) {
                this.ingredients[i] = new SingleRecipeComponent(new ItemStack(mat));
            } else if (ingredient instanceof final SlimefunTag tag) {
                this.ingredients[i] = new TagRecipeComponent(tag);
            } else if (ingredient instanceof final Set<?> group) {
                if (group.isEmpty()) {
                    this.ingredients[i] = RecipeComponent.EMPTY;
                }

                if (group.stream().findAny().get() instanceof ItemStack) {
                    this.ingredients[i] = new GroupRecipeComponent((Set<ItemStack>) group);
                }
            }
        }

        return this;
    }

    public RecipeBuilder ingredients(RecipeComponent<?>... ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public RecipeBuilder ingredients(ItemStack... ingredients) {
        return ingredients(Arrays.stream(ingredients)
                .map(ingredient -> ingredient == null ? null : new SingleRecipeComponent(ingredient))
                .toArray(RecipeComponent<?>[]::new));
    }

    public RecipeBuilder ingredients(Material... ingredients) {
        return ingredients(Arrays.stream(ingredients)
                .map(ingredient -> ingredient == null ? null : new SingleRecipeComponent(new ItemStack(ingredient)))
                .toArray(RecipeComponent<?>[]::new));
    }

    public RecipeBuilder output(RecipeOutput outputs) {
        this.output = outputs;
        return this;
    }

    public RecipeBuilder output(ItemStack... outputs) {
        this.output = new ItemRecipeOutput(outputs);
        return this;
    }

    public RecipeBuilder output(LootTable<ItemStack> outputs) {
        this.output = new WeightedRecipeOutput(outputs);
        return this;
    }

    public Recipe build() {
        return new Recipe(ingredientsProducer.apply(ingredients, shape), output);
    }

    public Recipe register() {
        final Recipe recipe = new Recipe(ingredientsProducer.apply(ingredients, shape), output);
        Recipe.registerRecipes(type, recipe);
        return recipe;
    }

}
