package me.epic.spigotlib.utils;

import org.bukkit.plugin.Plugin;

public class SchedulerUtils {

    public static void tenSecondDelay(Plugin plugin, Runnable runnable ) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 10);
    }

    public static void thirtySecondDelay(Plugin plugin, Runnable runnable ) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 30);
    }

    public static void oneMinuteDelay(Plugin plugin, Runnable runnable ) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 60);
    }

    public static void oneTickDelay(Plugin plugin, Runnable runnable) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 1);
    }

}
