package io.github.thebusybiscuit.slimefun4.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicate;

public final class RecipeUtils {

    public static class StripResult<T> {
        private final List<T> result;
        private final int strippedWidth;
        private final int strippedHeight;

        public StripResult(List<T> result, int strippedWidth, int strippedHeight) {
            this.result = result;
            this.strippedWidth = strippedWidth;
            this.strippedHeight = strippedHeight;
        }

        public List<T> getResult() {
            return result;
        }

        public int getStrippedWidth() {
            return strippedWidth;
        }

        public int getStrippedHeight() {
            return strippedHeight;
        }
    }

    private RecipeUtils() {}

    /**
     * Strips a 2d (flattened) list of null values on all 4 sides
     * 
     * @param <T>            The type of the list values
     * @param grid           The flattened list to strip
     * @param originalWidth  The width of the list
     * @param originalHeight The height of the list
     * @param isNullish      If returns true, the element is considered null
     * @return A new stripped flat list along with its width and height
     */
    public static <T> StripResult<T> strip(T[] grid, int originalWidth, int originalHeight, Predicate<T> isNullish) {
        if (grid.length != originalWidth * originalHeight) {
            return new StripResult<>(Arrays.asList(grid), originalWidth, originalHeight);
        }

        int startX = 0;
        int startY = 0;

        // Loop through each column, startX will be the first column to be non-empty
        columnLoop: for (int x = 0; x < originalWidth; x++) {
            for (int y = 0; y < originalHeight; y++) {
                if (!isNullish.apply(grid[originalWidth * y + x])) {
                    break columnLoop;
                }
            }
            startX++;
        }

        // Same logic but for rows
        rowLoop: for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                if (!isNullish.apply(grid[originalWidth * y + x])) {
                    break rowLoop;
                }
            }
            startY++;
        }

        int currentWidth = originalWidth - startX;
        int currentHeight = originalHeight - startY;

        // Loop through each column, backwards. w will be the first column to be
        // non-empty, minus startX
        reverseColumnLoop: for (int x = originalWidth - 1; x > startX; x--) {
            for (int y = 0; y < originalHeight; y++) {
                if (!isNullish.apply(grid[originalWidth * y + x])) {
                    break reverseColumnLoop;
                }
            }
            currentWidth--;
        }

        // Same logic but for rows
        reverseRowLoop: for (int y = originalHeight - 1; y > startY; y--) {
            for (int x = 0; x < originalWidth; x++) {
                if (!isNullish.apply(grid[originalWidth * y + x])) {
                    break reverseRowLoop;
                }
            }
            currentHeight--;
        }

        final List<T> newGrid = new ArrayList<>(currentWidth * currentHeight);

        // Copying over
        for (int x = 0; x < currentWidth; x++) {
            for (int y = 0; y < currentHeight; y++) {
                newGrid.add(currentWidth * y + x, grid[originalWidth * (startY + y) + startX + x]);
            }
        }

        return new StripResult<>(newGrid, currentWidth, currentHeight);
    }

}
