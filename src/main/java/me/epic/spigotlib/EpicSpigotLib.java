package me.epic.spigotlib;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicSpigotLib {

    private static Plugin plugin;

    public static void init(JavaPlugin plugin) {
        EpicSpigotLib.plugin = plugin;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
