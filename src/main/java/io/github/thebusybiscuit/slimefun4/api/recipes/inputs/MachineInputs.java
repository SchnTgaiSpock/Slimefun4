package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import java.util.function.Supplier;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeShape;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.RecipeComponent;

/**
 * A 'SUBSET' recipe that has a max length of 2
 */
public class MachineInputs extends CraftingGrid {

    public MachineInputs(RecipeComponent<?> input) {
        super(new RecipeComponent[] { input }, 1, 1, RecipeShape.SUBSET);
    }

    public MachineInputs(RecipeComponent<?> input1, RecipeComponent<?> input2) {
        super(new RecipeComponent[] { input1, input2 }, 2, 1, RecipeShape.SUBSET);
    }

    @Override
    public boolean matches(ItemStack[] inputs, Supplier<Boolean> canCraft, boolean consumeInputs) {
        return matchSubset(inputs, canCraft, consumeInputs);
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
