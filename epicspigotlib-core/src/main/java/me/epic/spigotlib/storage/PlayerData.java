package me.epic.spigotlib.storage;

import me.epic.spigotlib.PDT;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

/**
 * The PlayerData class represents the data associated with a player.
 */
public class PlayerData {

    private final Player player; // The player object.
    private PersistentDataContainer playerContainer; // The player's persistent data container.
    private final Plugin plugin; // The plugin instance.
    private NamespacedKey playerDataKey; // The key used to store the player's data in the persistent data container.

    /**
     * Constructs a new PlayerData object with the given player and plugin, using the default key.
     *
     * @param player the player object.
     * @param plugin the plugin instance.
     */
    public PlayerData(Player player, Plugin plugin) {
        this(player, plugin, new NamespacedKey(plugin, plugin.getDescription().getName() + "_Data"));
        createAndGetDataFile();
    }

    /**
     * Constructs a new PlayerData object with the given player, plugin, and key.
     *
     * @param player the player object.
     * @param plugin the plugin instance.
     * @param key the key used to store the player's data in the persistent data container.
     */
    public PlayerData(Player player, Plugin plugin, NamespacedKey key) {
        this.player = player;
        this.plugin = plugin;
        this.playerDataKey = key;
        this.playerContainer = player.getPersistentDataContainer();
        createAndGetDataFile();
    }

    /**
     * Creates a new data file for the player if one doesn't already exist.
     *
     * @return the player's data file.
     */
    public FileConfiguration createAndGetDataFile() {
        if (!playerContainer.has(playerDataKey, PDT.FILE_CONFIGURATION)) {
            FileConfiguration playerData = new YamlConfiguration();
            playerContainer.set(playerDataKey, PDT.FILE_CONFIGURATION, playerData);
        }
        return playerContainer.get(playerDataKey, PDT.FILE_CONFIGURATION);
    }

    /**
     * Gets the player's data file, creating a new one if one doesn't already exist.
     *
     * @return the player's data file.
     */
    public FileConfiguration getDataFile() {
        return playerContainer.getOrDefault(playerDataKey, PDT.FILE_CONFIGURATION, new YamlConfiguration());
    }

}