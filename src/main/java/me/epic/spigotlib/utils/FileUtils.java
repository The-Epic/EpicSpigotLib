package me.epic.spigotlib.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteStreams;

public class FileUtils {

	@Nonnull
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

	@Nonnull
	public static Optional<YamlConfiguration> loadResource(Plugin source, String resourceName) {
		Optional<File> optional = loadResourceFile(source, resourceName);

		if (optional.isPresent()) {
			return Optional.of(YamlConfiguration.loadConfiguration(optional.get()));
		} else {
			return Optional.empty();
		}
	}

	@Nonnull
	public static Optional<File> loadResourceFile(Plugin source, String resourceName, String outputName) {
		File resourceFile = new File(source.getDataFolder(), outputName);

		// Copy file if needed
		if (!resourceFile.exists() && source.getResource(resourceName) != null) {
			try {
				resourceFile.createNewFile();
				try (InputStream in = source.getResource(resourceName);
						OutputStream out = new FileOutputStream(resourceFile)) {
					ByteStreams.copy(in, out);
				}

			} catch (Exception e) {
				Bukkit.getLogger().severe("Error copying file " + resourceName);
				e.printStackTrace();
			}
		}
		if (!resourceFile.exists()) {
			return Optional.empty();
		}
		return Optional.of(resourceFile);
	}

	@Nonnull
	public static Optional<YamlConfiguration> loadResource(Plugin source, String resourceName, String outputName) {
		Optional<File> optional = loadResourceFile(source, resourceName, outputName);

		if (optional.isPresent()) {
			return Optional.of(YamlConfiguration.loadConfiguration(optional.get()));
		} else {
			return Optional.empty();
		}
	}
	
	public static void copyDefaults(JavaPlugin plugin, String path, Consumer<String> consumer) {
		final File jarFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

		if (jarFile.isFile()) {
			try (final JarFile jar = new JarFile(jarFile)) {
				final Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					final JarEntry entry = entries.nextElement();
					if (entry.isDirectory())
						continue;

					final String name = entry.getName();
					if (name.startsWith(path + "/")) {
						consumer.accept(name);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
