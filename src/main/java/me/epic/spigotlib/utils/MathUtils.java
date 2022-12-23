package me.epic.spigotlib.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtils {
	public static int generateNumberBetween(@NotNull Random random, int min, int max) {
		Preconditions.checkArgument(min <= max, "min (%s) must be <= max (%s)", min, max);

		if (random instanceof ThreadLocalRandom) {
			return ((ThreadLocalRandom) random).nextInt(min, max + 1);
		}

		return (min == max) ? min : min + (random.nextInt(max - min) + 1);
	}

}
