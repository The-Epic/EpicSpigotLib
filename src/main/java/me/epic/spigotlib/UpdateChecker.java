package me.epic.spigotlib;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

// From: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates
public class UpdateChecker {

	private final JavaPlugin plugin;
	private final int resourceId;

	public UpdateChecker(JavaPlugin plugin, int resourceId) {
		this.plugin = plugin;
		this.resourceId = resourceId;
	}

	public void getVersion(final Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL(
					"https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
					Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				plugin.getLogger().warning("Unable to check for updates: " + exception.getMessage());
			}
		});
	}

	/**
	 * Runs the update checker and sends message if update is found
	 *
	 * @param interval between update checks
	 * @param resourceLink spigot/whatever link
	 * @param enabled updateChecker enabled
	 */
	public void runUpdateChecker(Integer interval, String resourceLink, Boolean enabled) {
		SimpleSemVersion currentVersion = SimpleSemVersion.fromString(plugin.getDescription().getVersion());
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task -> {
			if (!enabled) task.cancel();
			this.getVersion(version -> {
				if (SimpleSemVersion.fromString(version).isNewerThan(currentVersion)) {
					ConsoleCommandSender console = Bukkit.getConsoleSender();
					console.sendMessage("-".repeat(50));
					console.sendMessage("A new version of " + plugin.getDescription().getName() + " is available: " + ChatColor.BOLD + version);
					console.sendMessage("Download it at " + resourceLink);
					console.sendMessage("-".repeat(50));
				}
			});
		}, 0, 20L * 60 * 60 * (interval == null ? 2 : interval));
	}
}