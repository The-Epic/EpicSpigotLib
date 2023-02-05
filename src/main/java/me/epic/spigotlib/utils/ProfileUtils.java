package me.epic.spigotlib.utils;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.UUID;

public class ProfileUtils {

    public static UUID getUUIDFromString(@Nonnull final String string) {
        if (string.length() == 36) return UUID.fromString(string);
        if (string.length() == 32) return fromStringWithoutDashes(string);
        throw new IllegalArgumentException("Not a valid UUID.");
    }

    private static UUID fromStringWithoutDashes(@Nonnull final String string) {
        return UUID.fromString(string.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }

    public static boolean isValidUUID(@Nonnull final String string) {
        return string.replace("-", "").matches("^\\p{XDigit}{32}$");
    }

    public static boolean isValidAccountName(@Nonnull final String name) {
        return name.matches("^\\w{3,16}$");
    }

    @Nonnull
    public static File getPlayerDataFile(UUID uuid) {
        File playerDataFolder = new File(WorldUtils.getDefaultWorld().getWorldFolder(), "playerdata");
        if(!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        return new File(playerDataFolder, uuid.toString() + ".dat");
    }


}
