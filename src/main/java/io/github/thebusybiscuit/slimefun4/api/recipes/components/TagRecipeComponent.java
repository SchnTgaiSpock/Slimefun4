package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

/**
 * A component (input) in a recipe. Matches all items in its tag.
 * 
 * @author SchnTgaiSpock
 */
public class TagRecipeComponent implements RecipeComponent<SlimefunTag> {

    private final SlimefunTag component;
    private final int amount;

    public TagRecipeComponent(@Nonnull SlimefunTag component, int amount) {
        this.component = component;
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        if (!SlimefunUtils.isItemSimilar(item, new ItemStack(item.getType(), getAmount()), true)) {
            return false;
        }
        return component.isTagged(item.getType());
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Nonnull
    @Override
    public ItemStack getDisplayItem() {
        if (isEmpty()) return null;
        return new ItemStack(component.stream().findFirst().get());
    }

    @Override
    public String toString() {
        return "TagRecipeComponent(" + getComponent().toString() + ")";
    }

    @Nonnull
    public SlimefunTag getComponent() {
        return component;
    }
    
}