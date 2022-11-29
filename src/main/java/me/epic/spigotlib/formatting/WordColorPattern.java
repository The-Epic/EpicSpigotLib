package me.epic.spigotlib.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordColorPattern implements ChatStylePattern {

    public static final Pattern PATTERN = Pattern.compile("<word:(.*)>");

    @Override
    // not deprecated for removal just hold over from old code
    // if need be can replicate functionality with a different method later
    @SuppressWarnings("deprecation")
    public String process(String message) {
        final Matcher matcher = PATTERN.matcher(message);
        while (matcher.find()) {
            final String color = matcher.group(1);

            try {
                final net.md_5.bungee.api.ChatColor possibleColor = net.md_5.bungee.api.ChatColor
                        .valueOf(color.toUpperCase());
                message = message.replace(matcher.group(), possibleColor + "");
            } catch (IllegalArgumentException ignore) {
                // ignore
            }
        }

        return message;
    }

}
