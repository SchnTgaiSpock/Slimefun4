package io.github.thebusybiscuit.slimefun4.api.recipes;

import java.util.Collections;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Preconditions;

/**
 * Stores detailed information about the result of matching a recipe against
 * some given inputs
 */
public class RecipeMatchResult {

    public static final RecipeMatchResult NO_MATCH = new RecipeMatchResult(false, Collections.emptyMap());

    @ParametersAreNonnullByDefault
    public static RecipeMatchResult match(Map<Integer, Integer> consumption, String message) {
        return new RecipeMatchResult(true, consumption, message);
    }
    
    @ParametersAreNonnullByDefault
    public static RecipeMatchResult match(Map<Integer, Integer> consumption) {
        return match(consumption, "");
    }

    private final boolean isMatch;
    private Map<Integer, Integer> consumption;
    private final String message;

    @ParametersAreNonnullByDefault
    public RecipeMatchResult(boolean isMatch, Map<Integer, Integer> consumption, String message) {
        Preconditions.checkNotNull(consumption, "The 'consumption' map cannot be null!");

        this.isMatch = isMatch;
        this.consumption = consumption;
        this.message = message == null ? "" : message;
    }

    @ParametersAreNonnullByDefault
    public RecipeMatchResult(boolean isMatch, Map<Integer, Integer> consumption) {
        this(isMatch, consumption, "");
    }

    /**
     * Whether or not the recipe be crafted (barring research, etc...)
     * @return
     */
    public boolean isMatch() {
        return isMatch;
    }

    public Map<Integer, Integer> getConsumption() {
        return consumption;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "RecipeMatchResult(match = " + isMatch + ", consumption map = " + consumption + ", message = " + message + ")";
    }

}