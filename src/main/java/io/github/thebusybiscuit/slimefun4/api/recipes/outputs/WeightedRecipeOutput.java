package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.LootTable;

public class WeightedRecipeOutput implements RecipeOutput {

    final LootTable<ItemStack> outputTable;

    public WeightedRecipeOutput(LootTable<ItemStack> outputTable) {
        this.outputTable = outputTable;
    }

    @Override
    public ItemStack[] getOutputs() {
        return new ItemStack[] { outputTable.generate().clone() };
    }
    
}