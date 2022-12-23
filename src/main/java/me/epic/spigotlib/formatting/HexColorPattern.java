package me.epic.spigotlib.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColorPattern implements ChatStylePattern {

    public static final Pattern PATTERN = Pattern
            .compile("<color:([0-9A-Fa-f]{6})>|#([0-9A-Fa-f]{6})|#(\\[[0-9A-Fa-f]{6}\\])");

    @Override
    public String process(String message) {
        final Matcher matcher = PATTERN.matcher(message);
        while (matcher.find()) {
            final String color = matcher.group(1) != null ? matcher.group(1)
                    : matcher.group(2) != null ? matcher.group(2) : matcher.group(3);

            message = message.replace(matcher.group(), Formatting.getColorFromHex(color) + "");
        }

        return message;
    }

}
