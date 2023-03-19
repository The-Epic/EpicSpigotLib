package me.epic.spigotlib.utils;

import me.epic.spigotlib.internal.annotations.NMS;
import me.epic.spigotlib.nms.NMSManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
//import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

import java.util.Objects;

/**
 * The WorldUtils class provides utility methods for interacting with game worlds.
 */
public class WorldUtils {

    /**
     * Returns the default world instance.
     *
     * @return the default world instance
     * @throws NullPointerException if the default world is null
     */
    public static World getDefaultWorld() throws NullPointerException {
        return Objects.requireNonNull(Bukkit.getWorld(getDefaultWorldName()), "Default world is null");
    }

    /**
     * Returns the default world name.
     *
     * @return the default world name
     */
    @NMS
    public static String getDefaultWorldName() {
        return NMSManager.getAdapter().getDefaultWorldName();
    }
}