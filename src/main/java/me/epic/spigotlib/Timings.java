package me.epic.spigotlib;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class Timings {
    private static final HashMap<String, Long> measurements = new HashMap<>();

    public static void startTimings(String identifier) {
        measurements.put(identifier, System.currentTimeMillis());
    }

    public static String endTimings(String identifier) {
        return endTimings(identifier, null, null);
    }

    public static void endTimings(String identifier, Plugin plugin) {
        endTimings(identifier, plugin, null);
    }

    public static void endTimings(String identifier, Player player) {
        endTimings(identifier, null, player);
    }

    private static String endTimings(String identifier, Plugin plugin, Player player) {
        Long now = System.currentTimeMillis();
        Long then = measurements.get(identifier);
        if (then == null) throw new IllegalArgumentException("No timer with identifier \"" + identifier + "\".");
        Long millisBetween = now-then;
        if (plugin != null) plugin.getLogger().info("Timer \"" + identifier + "\" finished in " + millisBetween + "ms");
        if (player != null) player.sendMessage("<green><bold>Timer \"" + identifier + "\" finished in " + millisBetween + "ms");
        return String.valueOf(millisBetween);
    }
}
