package me.epic.spigotlib.utils;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int generateNumberBetween(@NotNull Random random, int min, int max) {
        Preconditions.checkArgument(min <= max, "min (%s) must be <= max (%s)", min, max);

        if (random instanceof ThreadLocalRandom) {
            return ((ThreadLocalRandom) random).nextInt(min, max + 1);
        }

        return (min == max) ? min : min + (random.nextInt(max - min) + 1);
    }

    public static boolean chance100(final double chance) {
        return getDouble(0, 100) <= chance;
    }

    public static boolean chance(final double chance) {
        return getDouble(0, 1) <= chance;
    }

    public static double getDouble(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }



}
