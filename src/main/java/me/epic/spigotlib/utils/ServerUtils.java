package me.epic.spigotlib.utils;

import io.papermc.lib.PaperLib;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ServerUtils {

	private static final Server SERVER = Bukkit.getServer();

	public static String getOnlineMode() {

		if (SERVER.getOnlineMode()) {
			if (PaperLib.isPaper()) {
				return "Online Paper";
			}
			if (PaperLib.isSpigot()) {
				return "Online Spigot";
			}
		}

		if (SERVER.spigot().getConfig().getBoolean("settings.bungeecord", false))
			return "Bungee";

		return "Offline";
	}
}
