package me.epic.spigotlib.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.epic.spigotlib.SimpleSemVersion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class ConfigUpdater {
    private final JavaPlugin plugin;
    private final String versionPath;

    public ConfigUpdater saveDefault() {
        plugin.getDataFolder().mkdirs();
        if (!new File(plugin.getDataFolder(), "config.yml").exists())
            plugin.saveResource("config.yml", false);

        return this;
    }

    @Deprecated
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

    @SneakyThrows
    public void checkReplaceAndUpdate() {
        File oldConfig = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration resourceConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("config.yml")));
        FileConfiguration oldYamlConfig = YamlConfiguration.loadConfiguration(oldConfig);
        SimpleSemVersion newVersion = SimpleSemVersion.fromString(resourceConfig.getString(versionPath));
        if (newVersion.isNewerThan(SimpleSemVersion.fromString(oldYamlConfig.getString(versionPath)))) {
            oldConfig.renameTo(new File(plugin.getDataFolder().getCanonicalPath() + File.separator + "old-configs" + File.separator + "config.yml"));
        }
        plugin.saveResource("config.yml", false);
        File newConfigFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration newConfig = YamlConfiguration.loadConfiguration(newConfigFile);
        for (String key : oldYamlConfig.getKeys(false)) checkKey(key, oldYamlConfig, newConfig);

        newConfig.save(newConfigFile);
        plugin.saveConfig();

    }

    private void checkKey(String key, FileConfiguration oldConfig, FileConfiguration newConfig) {
        if (oldConfig.isConfigurationSection(key)) {
            for (String key2 : oldConfig.getConfigurationSection(key).getKeys(false)) {
                checkKey(key2, oldConfig, newConfig);
            }
        } else {
            if (!oldConfig.get(key).equals(newConfig.get(key))) newConfig.set(key, oldConfig.get(key));
        }
    }

}
