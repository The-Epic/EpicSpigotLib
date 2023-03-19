package me.epic.spigotlib;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Cooldowns class represents a utility class for managing cooldowns for a specified period of time, measured in seconds.
 * It uses a Map to store the cooldown times for each player.
 */
public class Cooldowns {
    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    /**
     * The default cooldown time in seconds.
     */
    public int defaultCooldown = 10;

    /**
     * Constructs a new Cooldowns object with the specified default cooldown time.
     *
     * @param defaultCooldown the default cooldown time in seconds
     */
    public Cooldowns(int defaultCooldown) {
        this.defaultCooldown = defaultCooldown;
    }

    /**
     * Sets the cooldown time for the specified player.
     *
     * @param player the UUID of the player to set the cooldown for
     * @param time the cooldown time in seconds
     */
    public void setCooldown(UUID player, int time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    /**
     * Gets the remaining cooldown time for the specified player.
     *
     * @param player the UUID of the player to get the cooldown for
     * @return the remaining cooldown time in seconds
     */
    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, defaultCooldown);
    }

}
