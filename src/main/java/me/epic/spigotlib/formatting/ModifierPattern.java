package me.epic.spigotlib.formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierPattern implements ChatStylePattern {

    public static final Pattern PATTERN = Pattern.compile("<mod:([A-Za-z]*)>(.[A-Za-z0-9]*)");

    @Override
    public String process(String message) {
        final Matcher matcher = PATTERN.matcher(message);
        while (matcher.find()) {
            final ModifierType type = ModifierType.fromString(matcher.group(1));
            final String text = matcher.group(2);
            message = message.replace(matcher.group(), ColourUtils.applyModifier(text, type));
        }

        return message;
    }

}
