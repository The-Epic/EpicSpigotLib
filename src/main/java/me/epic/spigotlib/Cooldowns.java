package me.epic.spigotlib;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldowns {
    private final Map<UUID, Integer> cooldowns = new HashMap<>();

    public final int defaultCooldown;

    public Cooldowns(int defaultCooldown) {
        this.defaultCooldown = defaultCooldown;
    }

    public void setCooldown(UUID player, int time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, defaultCooldown);
    }

}
