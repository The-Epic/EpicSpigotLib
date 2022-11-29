package me.epic.spigotlib;

import org.bukkit.Bukkit;

public class Version {
	
	public static final String V1_17_R1 = "v1_17_R1";
	public static final String V1_18_R1 = "v1_18_R1";
	public static final String V1_18_R2 = "v1_18_R2";
	public static final String V1_19_R1 = "v1_19_R1";
	public static final String V1_19_1_R1 = "v1_19_1_R1";
	public static final String V1_19_2_R1 = "v1_19_2_R1";

	public static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
}
