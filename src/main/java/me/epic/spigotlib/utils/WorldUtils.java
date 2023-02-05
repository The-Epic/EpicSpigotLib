package me.epic.spigotlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;

import javax.annotation.Nonnull;
import java.util.Objects;

public class WorldUtils {

    public static World getDefaultWorld() {
        return Objects.requireNonNull(Bukkit.getWorld(getDefaultWorldName()));
    }

    public static String getDefaultWorldName() {
        return ((CraftServer) Bukkit.getServer()).getServer().a().p;
    }

}
