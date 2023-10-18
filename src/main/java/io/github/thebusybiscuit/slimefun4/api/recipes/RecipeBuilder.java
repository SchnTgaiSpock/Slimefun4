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
import io.github.thebusybiscuit.slimefun4.api.recipes.inputs.RecipeInputs;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.ItemRecipeOutput;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.RecipeOutput;
import io.github.thebusybiscuit.slimefun4.api.recipes.outputs.WeightedRecipeOutput;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

public class RecipeBuilder {

    private RecipeType type = RecipeType.ENHANCED_CRAFTING_TABLE;
    private RecipeComponent<?>[] inputs = new RecipeComponent[9];
    private RecipeShape shape = RecipeShape.TRANSLATED;
    private RecipeOutput output = RecipeOutput.EMPTY;
    private BiFunction<RecipeComponent<?>[], RecipeShape, RecipeInputs> inputsProducer = CraftingGrid::new;

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
    public RecipeBuilder inputs(Object... inputs) {
        this.inputs = new RecipeComponent<?>[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            final Object input = inputs[i];
            if (input instanceof final ItemStack item) {
                this.inputs[i] = new SingleRecipeComponent(item);
            } else if (input instanceof final Material mat) {
                this.inputs[i] = new SingleRecipeComponent(new ItemStack(mat));
            } else if (input instanceof final SlimefunTag tag) {
                this.inputs[i] = new TagRecipeComponent(tag);
            } else if (input instanceof final Set<?> group) {
                if (group.isEmpty()) {
                    this.inputs[i] = RecipeComponent.EMPTY;
                }

                if (group.stream().findAny().get() instanceof ItemStack) {
                    this.inputs[i] = new GroupRecipeComponent((Set<ItemStack>) group);
                }
            }
        }

        return this;
    }

    public RecipeBuilder inputs(RecipeComponent<?>... inputs) {
        this.inputs = inputs;
        return this;
    }

    public RecipeBuilder inputs(ItemStack... inputs) {
        return inputs(Arrays.stream(inputs)
                .map(input -> input == null 
                    ? RecipeComponent.EMPTY
                    : new SingleRecipeComponent(input))
                .toArray(RecipeComponent<?>[]::new));
    }

    public RecipeBuilder inputs(Material... inputs) {
        return inputs(Arrays.stream(inputs)
                .map(input -> input == null
                    ? RecipeComponent.EMPTY
                    : new SingleRecipeComponent(new ItemStack(input)))
                .toArray(RecipeComponent<?>[]::new));
    }

    public RecipeBuilder outputs(RecipeOutput outputs) {
        this.output = outputs;
        return this;
    }

    public RecipeBuilder outputs(ItemStack... outputs) {
        this.output = new ItemRecipeOutput(outputs);
        return this;
    }

    public RecipeBuilder output(ItemStack output, int amount) {
        final ItemStack newOutput = output.clone();
        newOutput.setAmount(amount);
        this.output = new ItemRecipeOutput(newOutput);
        return this;
    }

    public RecipeBuilder output(Material output, int amount) {
        this.output = new ItemRecipeOutput(new ItemStack(output, amount));
        return this;
    }

    public RecipeBuilder output(Material output) {
        return output(output, 1);
    }

    public RecipeBuilder outputs(LootTable<ItemStack> outputs) {
        this.output = new WeightedRecipeOutput(outputs);
        return this;
    }

    public Recipe build() {
        return new Recipe(inputsProducer.apply(inputs, shape), output);
    }

    public Recipe register() {
        final Recipe recipe = new Recipe(inputsProducer.apply(inputs, shape), output);
        Recipe.registerRecipes(type, recipe);
        return recipe;
    }

}
