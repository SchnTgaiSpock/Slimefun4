package io.github.thebusybiscuit.slimefun4.api.recipes;

public enum RecipeShape {
    /**
     * A recipe with an identical RecipeShape will need each
     * input in the exact location as shown in the guide.
     */
    IDENTICAL,
    /**
     * A recipe with a translated RecipeShape will need each
     * input to have the same position relative to each other,
     * but the overall grid can be shifted
     */
    TRANSLATED,
    /**
     * A recipe with a shuffled RecipeShape will need each
     * item listed in the recipe to be present in the given
     * inputs and vice versa. Orientation and order don't matter.
     */
    SHUFFLED,
    /**
     * A recipe with a containing RecipeShape will only need
     * each item listed in the recipe to be present in the
     * given inputs. Orientation and order don't matter.
     */
    CONTAINING,
}
