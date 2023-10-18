package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeShape;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.RecipeComponent;

/**
 * A 'CONTAINING' recipe that has a max length of 2
 */
public class MachineInputs extends CraftingGrid {

    public MachineInputs(RecipeComponent<?> input) {
        super(new RecipeComponent[] { input }, 1, 1, RecipeShape.CONTAINING);
    }

    public MachineInputs(RecipeComponent<?> input1, RecipeComponent<?> input2) {
        super(new RecipeComponent[] { input1, input2 }, 2, 1, RecipeShape.CONTAINING);
    }

    @Override
    public boolean matches(ItemStack[] inputs, boolean consumeInputs) {
        return matchContaining(inputs, consumeInputs);
    }

    @Override
    public boolean isSingleItem() {
        return getInputs().length == 1;
    }

    @Override
    public MachineInputs toMachineInput() {
        return this;
    }
    
}
