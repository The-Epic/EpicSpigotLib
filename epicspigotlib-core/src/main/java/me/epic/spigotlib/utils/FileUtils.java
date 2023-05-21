package me.epic.spigotlib.utils;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;

public class FileUtils {

	/**
	 * Loads a resource file
	 *
	 * @param source JavaPlugin source
	 * @param resourceName Resource name to load
	 * @return The File
	 */
	@NotNull
	public static Optional<File> loadResourceFile(Plugin source, String resourceName) {
		File resourceFile = new File(source.getDataFolder(), resourceName);

		// Copy file if needed
		if (!resourceFile.exists() && source.getResource(resourceName) != null) {
			source.saveResource(resourceName, false);
		}

		// File still doesn't exist, return empty
		if (!resourceFile.exists()) {
			return Optional.empty();
		}
		return Optional.of(resourceFile);
	}

}
