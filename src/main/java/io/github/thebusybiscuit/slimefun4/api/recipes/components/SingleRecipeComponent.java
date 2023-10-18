package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

/**
 * A component (input) in a recipe. Matchines a single itemstack
 * 
 * @author SchnTgaiSpock
 */
public class SingleRecipeComponent implements RecipeComponent<ItemStack> {

    private final ItemStack component;

    public SingleRecipeComponent(@Nonnull ItemStack component) {
        this.component = component;
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        return SlimefunUtils.isItemSimilar(component, item, true);
    }

    @Override
    public boolean isEmpty() {
        return component.getType() == Material.AIR;
    }

    @Nonnull
    @Override
    public ItemStack getDisplayItem() {
        return component.clone();
    }

    @Override
    public String toString() {
        return "SingleRecipeComponent(" + getComponent().toString() + ")";
    }

    @Nonnull
    public ItemStack getComponent() {
        return component;
    }
    
}
