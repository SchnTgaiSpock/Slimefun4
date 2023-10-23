package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

/**
 * A component (input) in a recipe.
 * 
 * @author SchnTgaiSpock
 */
public interface RecipeComponent<T> {

    /**
     * Represents the empty component
     */
    public static SingleRecipeComponent EMPTY = new SingleRecipeComponent(new ItemStack(Material.AIR));

    public @Nonnull T getComponent();

    public int getAmount();

    /**
     * Determines if the given item can be used as this component in a recipe.
     * @param item The item to match
     * @return If the item is a match
     */
    public boolean matches(@Nullable ItemStack item);

    public boolean isEmpty();

    /**
     * Returns the item to be used in the recipe section of an item's guide entry
     * @return
     */
    public @Nonnull ItemStack getDisplayItem();

    @Nonnull
    public static SingleRecipeComponent of(@Nonnull ItemStack item) {
        return new SingleRecipeComponent(item);
    }

    @Nonnull 
    public static GroupRecipeComponent of(@Nonnull Set<ItemStack> group) {
        return new GroupRecipeComponent(group);
    }

    @Nonnull 
    public static TagRecipeComponent of(@Nonnull SlimefunTag tag, int amount) {
        return new TagRecipeComponent(tag, amount);
    }

    @Override
    public String toString();
    
}
