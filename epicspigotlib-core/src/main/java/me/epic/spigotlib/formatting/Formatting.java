package me.epic.spigotlib.formatting;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import java.awt.Color;

@SuppressWarnings("all")
public class Formatting {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder().character('&')
            .hexCharacter('#').hexColors().useUnusualXRepeatedCharacterHexFormat().build();

    /**
     * Formats a string
     *
     * @param toTranslate message to translate
     * @return Formatted String
     */
    public static String translate(String toTranslate) {
        return (ChatColor.translateAlternateColorCodes('&', SERIALIZER.serialize(MiniMessage.miniMessage().deserialize(toTranslate))));
    }

    /**
     * Formats a string
     *
     * @param toTranslate message to translate
     * @return Formatted String as a Component
     */
    public static Component translateComponent(String toTranslate) {
        return MiniMessage.miniMessage().deserialize(ChatColor.translateAlternateColorCodes('&', toTranslate));
    }

    /**
     * Returns ChatColour value from a hex
     *
     * @param colour to get the colour from
     * @return ChatColour value of colour
     */
    public static ChatColor getColourFromHex(String colour) {
        return ChatColor.of(new Color(Integer.parseInt(colour, 16)));
    }

}

