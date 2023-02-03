package me.epic.spigotlib.config;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;

public class ConfigUpdater {
    private final JavaPlugin plugin;

    public ConfigUpdater(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ConfigUpdater saveDefault() {
        plugin.getDataFolder().mkdirs();
        if (!new File(plugin.getDataFolder(), "config.yml").exists())
            plugin.saveResource("config.yml", false);

        return this;
    }

    @SneakyThrows
    public void checkAndUpdate() {
        FileConfiguration resourceConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("config.yml")));
        File config = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration currentConfig = YamlConfiguration.loadConfiguration(config);
        for (String key : resourceConfig.getKeys(true)) {
            if(currentConfig.contains(key)) continue;
            currentConfig.set(key, resourceConfig.get(key));
        }
        currentConfig.save(config);
        plugin.saveConfig();
    }
}
