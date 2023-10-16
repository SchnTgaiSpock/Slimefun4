package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

public class TagRecipeComponent implements RecipeComponent<SlimefunTag> {

    private final SlimefunTag component;

    public TagRecipeComponent(@Nonnull SlimefunTag component) {
        this.component = component;
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        if (!SlimefunUtils.isItemSimilar(new ItemStack(item.getType()), item, true)) {
            return false;
        }
        return component.isTagged(item.getType());
    }

    @Override
    public boolean isEmpty() {
        return component.isEmpty();
    }

    @Override
    public ItemStack getDisplayItem() {
        if (isEmpty()) return null;
        return new ItemStack(component.stream().findFirst().get());
    }

    @Override
    public String toString() {
        return "TagRecipeComponent(" + getComponent().toString() + ")";
    }

    public SlimefunTag getComponent() {
        return component;
    }
    
}
