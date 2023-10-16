package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

public class LoreAgnosticRecipeComponent implements RecipeComponent<ItemStack> {

    private final ItemStack component;

    public LoreAgnosticRecipeComponent(@Nonnull ItemStack component) {
        this.component = component;
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        return SlimefunUtils.isItemSimilar(component, item, false);
    }

    @Override
    public boolean isEmpty() {
        return component.getType() == Material.AIR;
    }

    @Override
    public ItemStack getDisplayItem() {
        return component.clone();
    }

    @Override
    public String toString() {
        return "LoreAgnosticRecipeComponent(" + getComponent().toString() + ")";
    }

    public ItemStack getComponent() {
        return component;
    }
    
}
