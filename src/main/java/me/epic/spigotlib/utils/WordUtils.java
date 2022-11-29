package me.epic.spigotlib.utils;

import java.util.Locale;

public class WordUtils {
    /**
     * Turns the first letter of a String to uppercase, while making the rest lowercase
     *
     * @param word String to change
     */
    public static String upperCaseFirstLetterOnly(final String word) {
        return upperCaseFirstLetter(word.toLowerCase(Locale.ROOT));
    }

    /**
     * Turns the first letter of a String to uppercase
     *
     * @param word String to change
     */
    public static String upperCaseFirstLetter(final String word) {
        if (word.length() < 1) return word;
        if (word.length() == 1) return word.toUpperCase(Locale.ROOT);
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
    }
}
