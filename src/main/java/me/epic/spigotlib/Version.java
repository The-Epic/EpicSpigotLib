package me.epic.spigotlib;

import org.bukkit.Bukkit;

public class Version {
	
	public static final String V1_17 = "1.17";
	public static final String V1_17_1 = "1.17.1";
	public static final String V1_18 = "1.18";
	public static final String V1_18_1 = "1.18.1";
	public static final String V1_18_2 = "1.18.2";
	public static final String V1_19 = "1.19";
	public static final String V1_19_1 = "1.19.1";
	public static final String V1_19_2 = "1.19.2";
	public static final String V1_19_3 = "1.19.3";


	public static String getServerVersion() {
		return Bukkit.getBukkitVersion().substring(0, Bukkit.getBukkitVersion().indexOf("-"));
	}

	public static String getServerVersionUnderscores() {
		return getServerVersion().replace(".", "_");
	}

}
