package me.epic.spigotlib.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class SchedulerUtils {

    public static BukkitTask tenSecondDelay(Plugin plugin, Runnable runnable ) {
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 10);
    }

    public static BukkitTask thirtySecondDelay(Plugin plugin, Runnable runnable ) {
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 30);
    }

    public static BukkitTask oneMinuteDelay(Plugin plugin, Runnable runnable ) {
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 60);
    }

    public static BukkitTask oneTickDelay(Plugin plugin, Runnable runnable) {
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 1);
    }

}
