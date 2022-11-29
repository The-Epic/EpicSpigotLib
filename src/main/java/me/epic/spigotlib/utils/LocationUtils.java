package me.epic.spigotlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LocationUtils {

	public static Player getNearestPlayer(Entity entity) {
		return getNearestPlayer(entity.getLocation());
	}

	public static Player getNearestPlayer(Location loc) {
		double playerDistance = Integer.MAX_VALUE;
		Player closest = null;

		for (Player player : Bukkit.getOnlinePlayers()) {
			double distance = player.getLocation().distanceSquared(loc);

			if (distance < playerDistance) {
				playerDistance = distance;
				closest = player;
			}
		}
		return closest;
	}

}
