package io.github.thebusybiscuit.slimefun4.api.recipes.outputs;

import org.bukkit.inventory.ItemStack;

public interface RecipeOutput {

    public static RecipeOutput EMPTY = new RecipeOutput() {
        @Override
        public ItemStack[] getOutputs() {
            return new ItemStack[0];
        }

        @Override
        public boolean isSingleItem() {
            return false;
        }

        @Override
        public boolean checkEnabled() {
            return true;
        }
    };
    
    public ItemStack[] getOutputs();
    public boolean isSingleItem();
    public boolean checkEnabled();

}
