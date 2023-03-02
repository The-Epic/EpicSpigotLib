package me.epic.spigotlib.language;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager {

    private final JavaPlugin plugin;
    private final Map<String, FileConfiguration> languages = new HashMap<>();
    private String enabledLanguage = "en_us";



    public LanguageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @SneakyThrows
    public void saveLanguages() {
    }
}
