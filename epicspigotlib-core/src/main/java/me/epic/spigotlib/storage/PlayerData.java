package me.epic.spigotlib.storage;

import me.epic.spigotlib.PDT;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

public class PlayerData {
    private final Player player;
    private PersistentDataContainer playerContainer;
    private final Plugin plugin;
    private NamespacedKey playerDataKey;


    public PlayerData(Player player, Plugin plugin) {
        this(player, plugin, new NamespacedKey(plugin, plugin.getDescription().getName() + "_Data"));
        createAndGetDataFile();
    }

    public PlayerData(Player player, Plugin plugin, NamespacedKey key) {
        this.player = player;
        this.plugin = plugin;
        this.playerDataKey = key;
        this.playerContainer = player.getPersistentDataContainer();
        createAndGetDataFile();
    }

    public FileConfiguration createAndGetDataFile() {
        if (!playerContainer.has(playerDataKey, PDT.FILE_CONFIGURATION)) {
            FileConfiguration playerData = new YamlConfiguration();
            playerContainer.set(playerDataKey, PDT.FILE_CONFIGURATION, playerData);
        }
        return playerContainer.get(playerDataKey, PDT.FILE_CONFIGURATION);
    }

    public FileConfiguration getDataFile() {
        return playerContainer.getOrDefault(playerDataKey, PDT.FILE_CONFIGURATION, new YamlConfiguration());
    }

}
