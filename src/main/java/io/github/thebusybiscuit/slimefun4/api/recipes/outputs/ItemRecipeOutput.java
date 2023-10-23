package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

public class ItemRecipeOutput implements RecipeOutput {

    private final ItemStack[] outputs;

    public ItemRecipeOutput(ItemStack... outputs) {
        this.outputs = new ItemStack[outputs.length];
        for (int i = 0; i < outputs.length; i++) {
            this.outputs[i] = outputs[i].clone();
        }
    }

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }

    @Override
    public boolean isSingleItem() {
        return outputs.length == 1;
    }

    @Override
    public boolean checkEnabled() {
        for (final ItemStack output : outputs) {
            final SlimefunItem sfItem = SlimefunItem.getByItem(output);
            if (sfItem != null && sfItem.isDisabled()) {
                return false;
            }
        }
        
        return true;
    }
    
}
