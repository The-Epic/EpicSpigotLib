package me.epic.spigotlib.material;

import me.epic.spigotlib.utils.WordUtils;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Iterator;

public class MaterialUtils {

    /**
     * Converts UPPERCASE underscore full material names to something easier to read
     *
     * @param mat to format
     * @return String of an easy-to-read Material name
     */
    public static String getNiceMaterialName(final Material mat) {
        final StringBuilder builder = new StringBuilder();
        final Iterator<String> iterator = Arrays.stream(mat.name().split("_")).iterator();
        while (iterator.hasNext()) {
            builder.append(WordUtils.upperCaseFirstLetterOnly(iterator.next()));
            if (iterator.hasNext()) builder.append(" ");
        }
        return builder.toString();
    }
}
