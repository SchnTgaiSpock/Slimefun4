package io.github.thebusybiscuit.slimefun4.api.recipes.inputs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeShape;
import io.github.thebusybiscuit.slimefun4.api.recipes.components.RecipeComponent;
import io.github.thebusybiscuit.slimefun4.utils.RecipeUtils;
import io.github.thebusybiscuit.slimefun4.utils.RecipeUtils.StripResult;

/**
 * A recipe input for a 3x3 crafting grid.
 * 
 * @apiNote Implementations for larger sized grids need only override:
 * <ul>
 * <li>CraftingGrid(inputs, shape)
 * <li>matchTranslated(inputs, consumeInputs)
 */
public class CraftingGrid extends RecipeInputs {

    private final RecipeComponent<?>[] inputs;
    private final int width;
    private final int height;
    private final RecipeShape shape;
    private final boolean singleItem;

    public CraftingGrid(RecipeComponent<?>[] inputs, int height, int width, RecipeShape shape) {
        this.shape = shape;
        switch (shape) {
            case IDENTICAL:
                this.inputs = inputs;
                this.height = height;
                this.width = width;
                break;

            case TRANSLATED:
                final var result = RecipeUtils.strip(
                        inputs,
                        width, height,
                        comp -> comp == null || comp.isEmpty());
                this.inputs = result.getResult().toArray(RecipeComponent<?>[]::new);
                this.width = result.getStrippedWidth();
                this.height = result.getStrippedHeight();
                break;

            default:
                this.inputs = Arrays
                        .stream(inputs)
                        .filter(comp -> comp == null || comp.isEmpty())
                        .toArray(RecipeComponent<?>[]::new);
                this.height = this.inputs.length;
                this.width = 1;
                break;
        }
        this.singleItem = Arrays.stream(inputs).reduce(
            0,
            (count, item) -> count += (item.isEmpty() ? 0 : 1),
            Integer::sum
        ) == 1;
    }

    public CraftingGrid(RecipeComponent<?>[] inputs, RecipeShape shape) {
        this(inputs, 3, 3, shape);
    }

    @Override
    public boolean matches(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        return switch (shape) {
            case IDENTICAL -> matchIdentical(inputs, consumeInputs);
            case TRANSLATED -> matchTranslated(inputs, consumeInputs);
            case SHUFFLED -> matchShuffled(inputs, consumeInputs);
            case CONTAINING -> matchContaining(inputs, consumeInputs);
            default -> false;
        };
    }

    public boolean matchIdentical(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        if (inputs.length != width * height) {
            return false;
        }

        for (int i = 0; i < inputs.length; i++) {
            if (!this.inputs[i].matches(inputs[i])) {
                return false;
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

    public boolean matchTranslated(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        if (inputs.length != 9) {
            return false;
        }

        final StripResult<ItemStack> result = RecipeUtils.strip(
                inputs,
                3, 3,
                item -> item == null || item.getType() == Material.AIR);
        final ItemStack[] reduced = result.getResult().toArray(ItemStack[]::new);

        return matchIdentical(reduced, consumeInputs);
    }

    public boolean matchShuffled(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        final ItemStack[] givenInputs = Arrays
                .stream(inputs)
                .filter(input -> input == null || input.getType() == Material.AIR)
                .toArray(ItemStack[]::new);

        if (givenInputs.length != this.inputs.length) {
            return false;
        }

        final Set<Integer> recipeInputsMatched = new HashSet<>();

        for (final ItemStack givenInput : givenInputs) {
            for (int i = 0; i < this.inputs.length; i++) {
                if (recipeInputsMatched.contains(i)) {
                    continue;
                }

                if (this.inputs[i].matches(givenInput)) {
                    recipeInputsMatched.add(i);
                }
            }
        }

        final boolean matched = recipeInputsMatched.size() == this.inputs.length;

        if (matched && consumeInputs) {
            for (ItemStack item : givenInputs) {
                item.setAmount(item.getAmount());
            }
        }

        return matched;
    }

    public boolean matchContaining(@Nonnull ItemStack[] inputs, boolean consumeInputs) {
        final ItemStack[] givenInputs = Arrays
                .stream(inputs)
                .filter(input -> input == null || input.getType() == Material.AIR)
                .toArray(ItemStack[]::new);

        if (givenInputs.length < this.inputs.length) {
            return false;
        }

        final Set<Integer> givenInputsMatched = new HashSet<>();
        final Set<Integer> recipeInputsMatched = new HashSet<>();

        for (int i = 0; i < givenInputs.length; i++) {
            for (int j = 0; j < this.inputs.length; j++) {
                if (recipeInputsMatched.contains(j)) {
                    continue;
                }

                if (this.inputs[j].matches(givenInputs[i])) {
                    givenInputsMatched.add(i);
                    recipeInputsMatched.add(j);
                }
            }
        }

        final boolean matched = recipeInputsMatched.size() == this.inputs.length;

        if (matched && consumeInputs) {
            givenInputsMatched.stream().forEach(i -> {
                final ItemStack item = givenInputs[i];
                item.setAmount(item.getAmount() - 1);
            });
        }

        return matched;
    }

    public RecipeComponent<?>[] getInputs() {
        return inputs;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public RecipeShape getShape() {
        return shape;
    }

    @Override
    public ItemStack[] getGuideRecipe() {
        return Arrays.stream(getInputs())
                .map(comp -> comp == null ? null : comp.getDisplayItem())
                .toArray(ItemStack[]::new);
    }

    @Override
    public List<ItemStack> getGuideBottomRows() {
        return List.of();
    }

    public boolean isSingleItem() {
        return singleItem;
    }

    public MachineInputs toMachineInput() {
        final RecipeComponent<?>[] filteredInputs = Arrays.stream(inputs)
            .filter(item -> item.isEmpty()).toArray(RecipeComponent<?>[]::new);

        return switch (filteredInputs.length) {
            case 1 -> new MachineInputs(inputs[0]);
            case 2 -> new MachineInputs(inputs[0], inputs[1]);
            default -> throw new IllegalStateException("Cannot convert this input into a MachineInput because it does not have only 1 or 2 items");
        };
    }

}
