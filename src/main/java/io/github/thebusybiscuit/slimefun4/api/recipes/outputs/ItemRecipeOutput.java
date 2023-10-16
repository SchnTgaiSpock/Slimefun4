package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

public class ItemRecipeOutput implements RecipeOutput {

    private final ItemStack[] outputs;

    public ItemRecipeOutput(ItemStack[] outputs) {
        this.outputs = new ItemStack[outputs.length];
        for (int i = 0; i < outputs.length; i++) {
            this.outputs[i] = outputs[i].clone();
        }
    }

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }
    
}
