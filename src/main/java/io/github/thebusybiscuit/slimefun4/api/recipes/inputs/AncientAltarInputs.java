package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import java.util.function.Supplier;

import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

import io.github.bakedlibs.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeShape;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.RecipeComponent;

/**
 * The inputs for an Ancient Altar Recipe
 * 
 * @author SchnTgaiSpock
 */
public class AncientAltarInputs extends CraftingGrid {

    private final RecipeComponent<?> catalyst;

    /**
     * 
     * @param inputs Inputs in clockwise order
     */
    public AncientAltarInputs(RecipeComponent<?> catalyst, @Nonnull RecipeComponent<?>... inputs) {
        super(inputs, 3, 3, RecipeShape.IDENTICAL);

        this.catalyst = catalyst;
    }

    /**
     * @param inputs The items in the Pedestals, in clockwise order, with the catalyst.
     * at the start. For the other inputs, any can be second, since all 8 rotations will be checked
     */
    @Override
    public boolean matches(@Nonnull ItemStack[] inputs, Supplier<Boolean> canCraft, boolean consumeInputs) {

        if (inputs.length != 9) {
            return false;
        }

        if (!catalyst.matches(inputs[0])) {
            return false;
        }

        final ItemStack[] inputsLoop = new ItemStack[16];
        for (int i = 0; i < 8; i++) {
            inputsLoop[i] = inputs[i+1];
            inputsLoop[i+8] = inputs[i+1];
        }
        
        rotatingLoop: for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                if (!getInputs()[j].matches(inputsLoop[j+i])) {
                    continue rotatingLoop;
                }
            }

            if (consumeInputs && canCraft.get()) {
                for (final ItemStack item : inputs) {
                    if (item != null) {
                        ItemUtils.consumeItem(item, 1, true);
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public MachineInputs toMachineInput() {
        throw new UnsupportedOperationException("Cannot convert AncientAlterInputs to MachineInputs");
    }
    
}
