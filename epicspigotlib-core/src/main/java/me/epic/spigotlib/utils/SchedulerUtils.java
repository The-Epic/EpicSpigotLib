package me.epic.spigotlib.utils;

import me.epic.spigotlib.EpicSpigotLib;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * A utility class for scheduling tasks with delays.
 */
public class SchedulerUtils {

    private static JavaPlugin plugin; // the plugin instance to use for scheduling tasks

    /**
     * Schedules a task to run after a delay of 10 seconds.
     * @param runnable The task to run.
     * @return A BukkitTask representing the scheduled task.
     */
    public static BukkitTask tenSecondDelay(Runnable runnable ) {
        initPlugin();
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 10);
    }

    /**
     * Schedules a task to run after a delay of 30 seconds.
     * @param runnable The task to run.
     * @return A BukkitTask representing the scheduled task.
     */
    public static BukkitTask thirtySecondDelay(Runnable runnable ) {
        initPlugin();
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 30);
    }

    /**
     * Schedules a task to run after a delay of 1 minute.
     * @param runnable The task to run.
     * @return A BukkitTask representing the scheduled task.
     */
    public static BukkitTask oneMinuteDelay(Runnable runnable ) {
        initPlugin();
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 20 * 60);
    }

    /**
     * Schedules a task to run after a delay of 1 tick.
     * @param runnable The task to run.
     * @return A BukkitTask representing the scheduled task.
     */
    public static BukkitTask oneTickDelay(Runnable runnable) {
        initPlugin();
        return plugin.getServer().getScheduler().runTaskLater(plugin, runnable, 1);
    }

    private static void initPlugin() {
        if (plugin == null) plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
    }

}
