package me.epic.spigotlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class LocationUtils {


	/**
	 * Get the nearest player around an Entity
	 *
	 * @param entity to check around
	 * @return Nearest Player
	 */
	public static Player getNearestPlayer(Entity entity) {
		return getNearestPlayer(entity.getLocation());
	}


	/**
	 * Gets the nearest player to a location
	 *
	 * @param location Of place to get
	 * @return Nearest Player
	 */
	public static Player getNearestPlayer(Location location) {
		double playerDistance = Integer.MAX_VALUE;
		Player closest = null;

		for (Player player : Bukkit.getOnlinePlayers()) {
			double distance = player.getLocation().distanceSquared(location);

			if (distance < playerDistance) {
				playerDistance = distance;
				closest = player;
			}
		}
		return closest;
	}

}
