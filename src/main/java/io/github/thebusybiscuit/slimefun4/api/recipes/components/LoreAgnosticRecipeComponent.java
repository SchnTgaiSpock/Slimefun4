package io.github.thebusybiscuit.slimefun4.api.recipes.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;

/**
 * A component (input) in a recipe. When matchine, it does not
 * take the lore of the items into account
 * 
 * @author SchnTgaiSpock
 */
public class LoreAgnosticRecipeComponent extends SingleRecipeComponent {

    public LoreAgnosticRecipeComponent(@Nonnull ItemStack component) {
        super(component);
    }

    @Override
    public boolean matches(@Nullable ItemStack item) {
        return SlimefunUtils.isItemSimilar(getComponent(), item, false);
    }

    @Override
    public String toString() {
        return "LoreAgnosticRecipeComponent(" + getComponent().toString() + ")";
    }
    
}
