package me.epic.spigotlib.language;

import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageConfig {

    private Map<String, String> stringMap = new HashMap<>();
    private Map<String, List<String>> stringListMap = new HashMap<>();

    private final File file;
    private YamlConfiguration configuration;

    public MessageConfig(File file) {
        this.file = file;
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void refresh() {
        this.stringMap.clear();
        this.stringListMap.clear();

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public String getString(String key) {
        if (this.stringMap.isEmpty() && (key.equals("no-permission") || key.equals("no_permission"))) {
            return Formatting.translate("<red>You do not have permission for this!");
        }  else {
            return this.stringMap.computeIfAbsent(key,
                    mapKey -> Formatting.translate(this.configuration.getString(key, "Error - Missing key: " + key)));
        }
    }

    public List<String> getStringList(String key) {
        return this.stringListMap.computeIfAbsent(key, mapKey -> parseStringList(key));
    }

    private List<String> parseStringList(String key) {
        List<String> list = new ArrayList<>();

        for (String string : this.configuration.getStringList(key)) {
            list.add(Formatting.translate(string));
        }
        return list;
    }

}
