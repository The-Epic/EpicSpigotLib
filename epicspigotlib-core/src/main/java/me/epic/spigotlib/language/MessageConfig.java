package me.epic.spigotlib.language;

import me.epic.spigotlib.EpicSpigotLib;
import me.epic.spigotlib.config.ConfigUpdater;
import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to load and manage messages from a YAML configuration file.
 */
public class MessageConfig {

    private Map<String, String> stringMap = new HashMap<>();
    private Map<String, List<String>> stringListMap = new HashMap<>();

    private final File file;
    private YamlConfiguration configuration;

    /**
     * Constructs a new `MessageConfig` object that loads messages from the specified YAML configuration file.
     *
     * @param file the YAML configuration file to load messages from
     */
    public MessageConfig(File file) {
        this.file = file;
        ConfigUpdater.update(EpicSpigotLib.getPlugin(), "messages.yml", file);
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Reloads the messages from the YAML configuration file.
     */
    public void refresh() {
        this.stringMap.clear();
        this.stringListMap.clear();

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Gets the message string associated with the specified key. If the key is not found in the YAML configuration
     * file, the message "Error - Missing key: [key]" is returned.
     *
     * @param key the key of the message to get
     * @return the message string associated with the specified key
     */
    public String getString(String key) {
        if (this.stringMap.isEmpty() && (key.equals("no-permission") || key.equals("no_permission"))) {
            return Formatting.translate("<red>You do not have permission for this!");
        }  else {
            return this.stringMap.computeIfAbsent(key,
                    mapKey -> Formatting.translate(this.configuration.getString(key, "Error - Missing key: " + key)));
        }
    }

    /**
     * Gets the list of message strings associated with the specified key. If the key is not found in the YAML
     * configuration file, an empty list is returned.
     *
     * @param key the key of the message list to get
     * @return the list of message strings associated with the specified key
     */
    public List<String> getStringList(String key) {
        return this.stringListMap.computeIfAbsent(key, mapKey -> parseStringList(key));
    }

    /**
     * Parses the list of message strings associated with the specified key, and returns it.
     *
     * @param key the key of the message list to parse
     * @return the list of message strings associated with the specified key
     */
    private List<String> parseStringList(String key) {
        List<String> list = new ArrayList<>();

        for (String string : this.configuration.getStringList(key)) {
            list.add(Formatting.translate(string));
        }
        return list;
    }

}