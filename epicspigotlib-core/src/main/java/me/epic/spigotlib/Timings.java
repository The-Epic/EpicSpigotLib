package me.epic.spigotlib;

import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Timings {
    private static final HashMap<String, Long> measurements = new HashMap<>();

    /**
     * Starts to record the time, using a String identifier, to be used in conjunction with {@link #endTimings(String)}
     *
     * @param identifier Name of the task to be measured
     */
    public static void startTimings(@NotNull String identifier) {
        measurements.put(identifier, System.currentTimeMillis());
    }

    /**
     * Ends a timings and returns the millis took
     *
     * @param identifier Name of the task to be ended
     * @return millis String formatted
     */
    public static String endTimingsString(@NotNull String identifier) {
        return String.valueOf(endTimings(identifier));
    }

    /**
     * Ends a timings and returns the millis took
     *
     * @param identifier Name of the task to be ended
     * @return millis Long formatted
     */
    public static long endTimings(@NotNull String identifier) {
        return endTimings(identifier, null, null);
    }

    /**
     * Ends a timings and returns the millis took
     *
     * @param identifier Name of the task to be ended
     * @param plugin Plugin to log messages under
     * @return millis Long formatted
     */
    public static long endTimings(@NotNull String identifier,@NotNull  Plugin plugin) {
        return endTimings(identifier, plugin, null);
    }
    /**
     * Ends a timings and returns the millis took
     *
     * @param identifier Name of the task to be ended
     * @param player Player to send messages to
     * @return millis Long formatted
     */
    public static long endTimings(@NotNull String identifier,@NotNull  Player player) {
        return endTimings(identifier, null, player);
    }

    /**
     * Ends a timings and returns the millis took
     *
     * @param identifier Name of the task to be ended
     * @param plugin Plugin to log messages under
     * @param player Player to send messages to
     * @return millis Long formatted
     */
    public static long endTimings(@NotNull String identifier, @Nullable Plugin plugin, @Nullable Player player) {
        Long now = System.currentTimeMillis();
        Long then = measurements.get(identifier);
        measurements.remove(identifier);
        if (then == null) throw new IllegalArgumentException("No timer with identifier \"" + identifier + "\".");
        Long millisBetween = now-then;
        if (plugin != null) plugin.getLogger().info("Timer \"" + identifier + "\" finished in " + millisBetween + "ms");
        if (player != null) player.sendMessage(Formatting.translate("<green><bold>Timer \"" + identifier + "\" finished in " + millisBetween + "ms"));
        return millisBetween;
    }
}
