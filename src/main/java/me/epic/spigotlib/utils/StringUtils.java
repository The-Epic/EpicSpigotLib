package me.epic.spigotlib.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class StringUtils {
	private static Map<Integer, Pattern> regexCache = new HashMap<>();

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
	 * Converts a number to roman numerals
	 *
	 * @param number to convert
	 * @return The specified number in roman numeral
	 */
	public static final String toRomanNumeral(int number) {
		int l = romanMap.floorKey(number);
		if (number == l) {
			return romanMap.get(number);
		}
		return romanMap.get(l) + toRomanNumeral(number - l);
	}
}
