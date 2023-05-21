package me.epic.spigotlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class ServerUtils {

	private static final Server SERVER = Bukkit.getServer();

	/**
	 * Gets the type of the server
	 *
	 * @return Type of server
	 */
	public static String getMode() {

		if (SERVER.getOnlineMode())
			return "Online";


		if (SERVER.spigot().getConfig().getBoolean("settings.bungeecord", false))
			return "Bungee";

		return "Offline";
	}

	/**
	 * Gets whether this server is running MockBukkit
	 */
	public static boolean isRunningMockBukkit() {
		return SERVER.getClass().getName().equals("be.seeseemelk.mockbukkit.ServerMock");
	}
}
