package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

import io.github.bakedlibs.dough.collections.RandomizedSet;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

public class WeightedRecipeOutput implements RecipeOutput {

    final RandomizedSet<ItemStack> outputSet;

    public WeightedRecipeOutput(RandomizedSet<ItemStack> outputSet) {
        this.outputSet = outputSet;
    }

    @Override
    public ItemStack[] getOutputs() {
        return new ItemStack[] { outputSet.getRandom().clone() };
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean checkEnabled() {
        for (final ItemStack output : outputSet) {
            final SlimefunItem sfItem = SlimefunItem.getByItem(output);
            if (sfItem != null && sfItem.isDisabled()) {
                return false;
            }
        }
        
        return true;
    }
    
}