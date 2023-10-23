package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

/**
 * A component (input) in a recipe. It matches any one of its items
 * 
 * @author SchnTgaiSpock
 */
public class GroupRecipeComponent implements RecipeComponent<Set<ItemStack>> {

    private final Set<ItemStack> component;

    /**
     * @param component A non-empty set of possible itemstacks to match
     */
    public GroupRecipeComponent(@Nonnull Set<ItemStack> component) {
        Validate.notEmpty(component, "The component of a GroupRecipeComponent cannot be empty");
        this.component = component;
    }

    @Override
    public int getAmount() {
        return component.stream().findFirst().get().getAmount();
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        return component.stream().anyMatch(comp -> SlimefunUtils.isItemSimilar(item, comp, true));
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getDisplayItem() {
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
