package me.epic.spigotlib.language;

import lombok.SneakyThrows;
import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class LanguageManager {

    private final JavaPlugin plugin;
    @Getter
    private final List<String> languagesAvailable = new ArrayList<>();
    @Getter @Setter
    private String enabledLanguage;
    private FileConfiguration enabledLanguageConfig;

    /**
     * Manual initalization
     *
     * @param plugin to initalize on
     */
    public LanguageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    /**
     * Saves all languages found resources/languages
     *
     * @return LanguageManager instance
     */
    public LanguageManager saveLanguages() {
        processLanguages(name -> plugin.saveResource(name, false));

        return this;
    }

    @SneakyThrows
    private void processLanguages(Consumer<String> consumer) {
        final File jarFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if (jarFile.isFile()) {
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) continue;

                final String name = entry.getName();
                if (name.startsWith("languages" + "/")) {
                    if (plugin.getDataFolder().exists() && !(new File(plugin.getDataFolder(), name).exists()))
                        consumer.accept(name);
                    languagesAvailable.add(name.replace("languages/", "").replace(".yml", ""));
                }
            }
        }
    }


    /**
     * Automatic setup, to save languages, add comments with available languages to config, and setups the config used for messages
     *
     * @param plugin To setup using
     * @param languagePath path to location string in {@link JavaPlugin#getConfig()}
     * @return LanguageManager instance
     */
    public static LanguageManager fullSetup(JavaPlugin plugin, String languagePath) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        LanguageManager manager = new LanguageManager(plugin);
        manager.saveLanguages();
        List<String> comments = new ArrayList<>();
        comments.add("Available languages: ");
        manager.getLanguagesAvailable().forEach(lang -> comments.add("-" + lang));
        config.setComments(languagePath, comments);
        plugin.saveConfig();
        String enabledLanguage = config.getString(languagePath, "en_US");
        if (!manager.getLanguagesAvailable().contains(enabledLanguage)) {
            plugin.getLogger().severe("Language specified does not exist, disabling.");
            Bukkit.getPluginManager().disablePlugin(plugin);
            throw new IllegalArgumentException();
        }
        manager.setEnabledLanguage(enabledLanguage);
        manager.reloadEnabledConfig();
        return manager;
    }

    /**
     * Reloads the config used for messages
     */
    public void reloadEnabledConfig() {
        enabledLanguageConfig = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + File.separator + "languages", enabledLanguage + ".yml"));
    }

    /**
     * Get and formats a String from the enabled language config
     *
     * @param path to message
     * @return Formatted message string
     */
    public String getString(String path) {
        return Formatting.translate(String.valueOf(enabledLanguageConfig.get(path)));
    }

    /**
     * Gets and formats a String list from the enabled language config
     *
     * @param path to string list
     * @return Formatted string list
     */
    public List<String> getStringList(String path) {
        return enabledLanguageConfig.getStringList(path).stream().map(Formatting::translate).collect(Collectors.toList());
    }
}