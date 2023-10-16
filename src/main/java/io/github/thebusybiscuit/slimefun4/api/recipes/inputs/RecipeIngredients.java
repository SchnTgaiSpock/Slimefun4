package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public abstract class RecipeIngredients {

    public static final RecipeIngredients EMPTY = new RecipeIngredients() {

        @Override
        public boolean matches(ItemStack[] ingredients, boolean consumeIngredients) {
            for (ItemStack item : ingredients) {
                if (item != null && item.getType() != Material.AIR) {
                    return false;
                }
            }
            
            return true;
        }

        @Override
        public ItemStack[] getGuideRecipe() {
            return new ItemStack[9];
        }

        @Override
        public List<ItemStack> getGuideBottomRows() {
            return List.of();
        }

    };

    public abstract boolean matches(@Nonnull ItemStack[] ingredients, boolean consumeIngredients);
    public abstract ItemStack[] getGuideRecipe();
    public abstract List<ItemStack> getGuideBottomRows();
    
}
