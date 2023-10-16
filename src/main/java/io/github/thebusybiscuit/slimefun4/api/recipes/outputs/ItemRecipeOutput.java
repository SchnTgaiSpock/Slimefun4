package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

public class ItemRecipeOutput implements RecipeOutput {

    private final ItemStack[] outputs;

    public ItemRecipeOutput(ItemStack[] outputs) {
        this.outputs = outputs;
    }

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }
    
}
