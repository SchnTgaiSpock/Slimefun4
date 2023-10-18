package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

/**
 * A component (input) in a recipe. It matches any one of its items
 * 
 * @author SchnTgaiSpock
 */
public class GroupRecipeComponent implements RecipeComponent<Set<ItemStack>> {

    private final Set<ItemStack> component;

    public GroupRecipeComponent(@Nonnull Set<ItemStack> component) {
        this.component = component;
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        return component.stream().anyMatch(comp -> SlimefunUtils.isItemSimilar(comp, item, true));
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getDisplayItem() {
        if (isEmpty()) return null;
        return component.stream().findFirst().get().clone();
    }

    @Override
    public String toString() {
        return "GroupRecipeComponent(" + getComponent().toString() + ")";
    }

    @Nonnull
    public Set<ItemStack> getComponent() {
        return component;
    }
    
}
