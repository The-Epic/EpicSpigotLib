package me.epic.spigotlib.material;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class MaterialUtils {
    public static String getNiceMaterialName(final Material mat) {
        final StringBuilder builder = new StringBuilder();
        final Iterator<String> iterator = Arrays.stream(mat.name().split("_")).iterator();
        while (iterator.hasNext()) {
            builder.append(upperCaseFirstLetterOnly(iterator.next()));
            if (iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }

    private static String upperCaseFirstLetterOnly(final String word) {
        return upperCaseFirstLetter(word.toLowerCase(Locale.ROOT));
    }

    private static String upperCaseFirstLetter(final String word) {
        if (word.length() < 1) return word;
        if (word.length() == 1) return word.toUpperCase(Locale.ROOT);
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1);
    }

    public static Material getMaterial(String mat, Material def) {
        Material material = Material.getMaterial(mat.toUpperCase());
        return material == null ? def : material;
    }
}
