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

	private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)([wdhms])");

	public static double getRandomDouble(double min, double max) {
		return ThreadLocalRandom.current().nextDouble() * (max - min);
	}

	public static int generateNumberBetween(int min, int max) {
		return generateNumberBetween(ThreadLocalRandom.current(), min, max);
	}

	public static int generateNumberBetween(@NotNull Random random, int min, int max) {
		Preconditions.checkArgument(min <= max, "min (%s) must be <= max (%s)", min, max);

		if (random instanceof ThreadLocalRandom) {
			return ((ThreadLocalRandom) random).nextInt(min, max + 1);
		}

		return (min == max) ? min : min + (random.nextInt(max - min) + 1);
	}

	public static long parseSeconds(@Nullable String value) {
		if (value == null) {
			return 0;
		}

		// Handle legacy (i.e. no timestamps... for example, just "600")
		int legacyTime = NumberUtils.toInt(value, -1);
		if (legacyTime != -1) {
			return legacyTime;
		}

		int seconds = 0;

		Matcher matcher = TIME_PATTERN.matcher(value);
		while (matcher.find()) {
			int amount = NumberUtils.toInt(matcher.group(1));

			switch (matcher.group(2)) {
				case "w":
					seconds += TimeUnit.DAYS.toSeconds(amount * 7);
					break;
				case "d":
					seconds += TimeUnit.DAYS.toSeconds(amount);
					break;
				case "h":
					seconds += TimeUnit.HOURS.toSeconds(amount);
					break;
				case "m":
					seconds += TimeUnit.MINUTES.toSeconds(amount);
					break;
				case "s":
					seconds += amount;
					break;
			}
		}

		return seconds;
	}

}
