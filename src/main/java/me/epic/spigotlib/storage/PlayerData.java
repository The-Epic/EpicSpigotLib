package me.epic.spigotlib.storage;

import me.epic.spigotlib.EpicSpigotLib;
import me.epic.spigotlib.PDT;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerData {
    private final Player player;
    private final Plugin plugin;
    private NamespacedKey playerDataKey;

    public PlayerData(Player player) {
        this.player = player;
        this.plugin = EpicSpigotLib.getPlugin();
        this.playerDataKey = new NamespacedKey(plugin, plugin.getDescription().getName() + "_Data");
        createDataFile();
    }

    public PlayerData(Player player, Plugin plugin) {
        this.player = player;
        this.plugin = plugin;
        this.playerDataKey = new NamespacedKey(plugin, plugin.getDescription().getName() + "_Data");
        createDataFile();
    }

    public PlayerData(Player player, Plugin plugin, NamespacedKey key) {
        this.player = player;
        this.plugin = plugin;
        this.playerDataKey = key;
        createDataFile();
    }

    public void createDataFile() {
        FileConfiguration playerData = new YamlConfiguration();
        player.getPersistentDataContainer().set(playerDataKey, PDT.FILE_CONFIGURATION, playerData);
    }

    public FileConfiguration getPlayerData() {
        return player.getPersistentDataContainer().getOrDefault(playerDataKey, PDT.FILE_CONFIGURATION, new YamlConfiguration());
    }


}
