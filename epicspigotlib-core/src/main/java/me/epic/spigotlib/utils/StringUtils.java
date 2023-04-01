package me.epic.spigotlib.utils;

import org.bukkit.ChatColor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for handling and manipulating strings.
 */
public class StringUtils {

	/**
	 * A cache of compiled regular expression patterns for splitting strings into chunks of a certain size.
	 */
	private static Map<Integer, Pattern> regexCache = new HashMap<>();

	/**
	 * A mapping of integer values to their corresponding Roman numeral symbols.
	 */
	private static final TreeMap<Integer, String> romanMap = new TreeMap<>();
	static {
		romanMap.put(1000, "M");
		romanMap.put(900, "CM");
		romanMap.put(500, "D");
		romanMap.put(400, "CD");
		romanMap.put(100, "C");
		romanMap.put(90, "XC");
		romanMap.put(50, "L");
		romanMap.put(40, "XL");
		romanMap.put(10, "X");
		romanMap.put(9, "IX");
		romanMap.put(5, "V");
		romanMap.put(4, "IV");
		romanMap.put(1, "I");
	}

	/**
	 * Splits a string into chunks of a specified size, ignoring any partial words at the end of the string.
	 *
	 * @param string the string to be split
	 * @param size the size of each chunk
	 * @return a list of chunks of the specified size
	 */
	public static List<String> splitString(String string, int size) {
		Pattern pattern = regexCache.computeIfAbsent(size,
				key -> Pattern.compile("(?<![^\\s]).{1," + size + "}(?![^\\s])"));

		List<String> result = new ArrayList<>();
		String prev = "";

		Matcher matcher = pattern.matcher(string);

		while (matcher.find()) {
			String next = ChatColor.getLastColors(prev) + matcher.group().trim();
			result.add(next);
			prev = next;
		}

		return result;
	}

	/**
	 * Converts an integer to its equivalent Roman numeral representation.
	 *
	 * @param number the integer to be converted
	 * @return the specified integer in Roman numeral form
	 */
	public static final String toRomanNumeral(int number) {
		int l = romanMap.floorKey(number);
		if (number == l) {
			return romanMap.get(number);
		}
		return romanMap.get(l) + toRomanNumeral(number - l);
	}

	/**
	 * Scrambles word(s)
	 *
	 * @param word or words to scramble
	 * @return Scrambled word or words
	 */
	public static String scrambleWord(String word) {
		String[] words = word.split(" ");
		StringBuilder builder = new StringBuilder(word.length());

		for (String wrd : words) {
			List<Character> characters = new ArrayList<>(wrd.chars().mapToObj(i -> (char) i).toList());
			Collections.shuffle(characters);

			for (char character : characters) {
				builder.append(character);
			}
			builder.append(' ');
		}
		return builder.toString().trim();
	}
}
