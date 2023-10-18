package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A list of RecipeComponents that constitute a recipe
 */
public abstract class RecipeInputs {

    public static final RecipeInputs EMPTY = new RecipeInputs() {

        @Override
        public boolean matches(ItemStack[] inputs, boolean consumeInputs) {
            for (final ItemStack item : inputs) {
                if (item != null && item.getType() != Material.AIR) {
                    return false;
                }
            }
            
            return true;
        }

        @Nonnull
        @Override
        public ItemStack[] getGuideRecipe() {
            return new ItemStack[9];
        }

        @Override
        public boolean isSingleItem() {
            return false;
        }

    };

    public static final RecipeInputs NO_MATCH = new RecipeInputs() {

        @Override
        public boolean matches(ItemStack[] inputs, boolean consumeInputs) {
            return false;
        }

        @Nonnull
        @Override
        public ItemStack[] getGuideRecipe() {
            return new ItemStack[9];
        }

        @Override
        public boolean isSingleItem() {
            return false;
        }

    };

    public abstract boolean matches(@Nonnull ItemStack[] inputs, boolean consumeInputs);
    public abstract @Nonnull ItemStack[] getGuideRecipe();
    public abstract boolean isSingleItem();

    @Nonnull
    public List<ItemStack> getGuideBottomRows() {
        return Collections.emptyList();
    }
    
}
