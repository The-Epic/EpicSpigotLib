package me.epic.spigotlib.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

/**
 * A utility class for working with player profiles and UUIDs.
 */
public class ProfileUtils {

    /**
     * Converts a string to a UUID.
     *
     * @param string the string to convert
     * @return the UUID
     * @throws IllegalArgumentException if the string is not a valid UUID
     */
    public static UUID getUUIDFromString(@NotNull final String string) {
        if (string.length() == 36) return UUID.fromString(string);
        if (string.length() == 32) return fromStringWithoutDashes(string);
        throw new IllegalArgumentException("Not a valid UUID.");
    }

    /**
     * Converts a string without dashes to a UUID.
     *
     * @param string the string to convert
     * @return the UUID
     * @throws IllegalArgumentException if the string is not a valid UUID
     */
    private static UUID fromStringWithoutDashes(@NotNull final String string) {
        return UUID.fromString(string.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }

    /**
     * Checks if a string is a valid UUID.
     *
     * @param string the string to check
     * @return true if the string is a valid UUID, false otherwise
     */
    public static boolean isValidUUID(@NotNull final String string) {
        return string.replace("-", "").matches("^\\p{XDigit}{32}$");
    }

    /**
     * Checks if a player account name is valid.
     *
     * @param name the player account name to check
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidAccountName(@NotNull final String name) {
        return name.matches("^\\w{3,16}$");
    }

    /**
     * Gets the file for the specified player data UUID.
     *
     * @param uuid the player data UUID
     * @return the player data file
     */
    @NotNull
    public static File getPlayerDataFile(UUID uuid) {
        File playerDataFolder = new File(WorldUtils.getDefaultWorld().getWorldFolder(), "playerdata");
        if(!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        return new File(playerDataFolder, uuid.toString() + ".dat");
    }
}