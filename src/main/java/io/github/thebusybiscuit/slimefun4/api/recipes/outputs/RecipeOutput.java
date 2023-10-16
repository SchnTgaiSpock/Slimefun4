package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface RecipeOutput {

    public static RecipeOutput EMPTY = new RecipeOutput() {
        @Override
        public ItemStack[] getOutputs() {
            return new ItemStack[0];
        }
    };
    
    public ItemStack[] getOutputs();

}
