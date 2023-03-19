package me.epic.spigotlib.utils;

import me.epic.spigotlib.nms.NMSManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
//import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

import java.util.Objects;

public class WorldUtils {

    public static World getDefaultWorld() {
        return Objects.requireNonNull(Bukkit.getWorld(getDefaultWorldName()));
    }

    public static String getDefaultWorldName() {
        return NMSManager.getAdapter().getDefaultWorldName();
    }

}
