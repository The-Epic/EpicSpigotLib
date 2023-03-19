package me.epic.spigotlib.utils;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class for generating random numbers and performing chance-based operations.
 */
public class RandomUtils {

    /**
     * Generates a random integer between the specified minimum (inclusive) and maximum (inclusive) values.
     *
     * @param random the random number generator to use
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random integer between the specified minimum and maximum values
     * @throws IllegalArgumentException if the minimum value is greater than the maximum value
     */
    public static int generateNumberBetween(@NotNull Random random, int min, int max) {
        Preconditions.checkArgument(min <= max, "min (%s) must be <= max (%s)", min, max);

        if (random instanceof ThreadLocalRandom) {
            return ((ThreadLocalRandom) random).nextInt(min, max + 1);
        }

        return (min == max) ? min : min + (random.nextInt(max - min) + 1);
    }

    /**
     * Determines if an event with a probability of 100% * {@code chance} should occur.
     *
     * @param chance the chance (expressed as a decimal between 0 and 1) of the event occurring
     * @return {@code true} if the event should occur, otherwise {@code false}
     */
    public static boolean chance100(final double chance) {
        return getDouble(0, 100) <= chance;
    }

    /**
     * Determines if an event with a probability of {@code chance} should occur.
     *
     * @param chance the chance (expressed as a decimal between 0 and 1) of the event occurring
     * @return {@code true} if the event should occur, otherwise {@code false}
     */
    public static boolean chance(final double chance) {
        return getDouble(0, 1) <= chance;
    }

    /**
     * Generates a random double between the specified minimum (inclusive) and maximum (exclusive) values.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (exclusive)
     * @return a random double between the specified minimum and maximum values
     */
    public static double getDouble(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}