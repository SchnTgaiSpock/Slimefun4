package io.github.thebusybiscuit.slimefun4.api.recipes.matching;

import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.items.AbstractRecipeInputItem;

public class ItemMatchResult {

    private final boolean itemsMatch;
    private final AbstractRecipeInputItem recipeItem;
    private final @Nullable ItemStack matchedItem;

    public ItemMatchResult(boolean itemsMatch, AbstractRecipeInputItem recipeItem, ItemStack matchedItem) {
        this.itemsMatch = itemsMatch;
        this.recipeItem = recipeItem;
        this.matchedItem = matchedItem;
    }
    
    /**
     * @return True if the provided items match the recipe items
     */
    public boolean itemsMatch() { return itemsMatch; }
    /**
     * @return The item in the recipe that was being matched to
     */
    public AbstractRecipeInputItem getRecipeItem() { return recipeItem; }
    /**
     * @return The item provided that was being matched
     */
    @Nullable
    public ItemStack getMatchedItem() { return matchedItem; }
    
}
