package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeShape;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.RecipeComponent;

/**
 * The inputs for an Ancient Altar Recipe
 * 
 * @author SchnTgaiSpock
 */
public class AncientAltarInputs extends CraftingGrid {

    /**
     * 
     * @param inputs Inputs in clockwise order
     */
    public AncientAltarInputs(@Nonnull RecipeComponent<?>... inputs) {
        super(inputs, 8, 1, RecipeShape.IDENTICAL);
    }

    /**
     * @param inputs The items in the Pedestals, in clockwise order.
     * Any item can be the start, since all 8 rotations will be checked
     */
    @Override
    public boolean matches(@Nonnull ItemStack[] inputs, boolean consumeInputs) {

        if (inputs.length != 8) {
            return false;
        }

        final ItemStack[] inputsLoop = new ItemStack[16];
        for (int i = 0; i < 8; i++) {
            inputsLoop[i] = inputs[i];
            inputsLoop[i+8] = inputs[i];
        }
        
        rotatingLoop: for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                if (!getInputs()[j].matches(inputsLoop[j+i])) {
                    continue rotatingLoop;
                }
            }

            if (consumeInputs) {
                for (final ItemStack item : inputs) {
                    if (item != null) {
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean isSingleItem() {
        return false;
    }

    @Override
    public MachineInputs toMachineInput() {
        throw new UnsupportedOperationException("Cannot convert AncientAlterInputs to MachineInputs");
    }
    
}
