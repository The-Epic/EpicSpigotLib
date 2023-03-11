package me.epic.spigotlib.config;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigUpdater {
    //Used for separating keys in the keyBuilder inside parseComments method
    private static final char SEPARATOR = '.';

    /**
     * Runs the config updater automatically
     *
     * @param plugin to run for
     */
    public static void runConfigUpdater(Plugin plugin) {
        plugin.saveDefaultConfig();
        update(plugin, "config.yml", new File(plugin.getDataFolder(), "config.yml"));
        plugin.reloadConfig();
    }

    /**
     * Manually initiate the config updater, requires {@link JavaPlugin#saveDefaultConfig()} and {@link JavaPlugin#reloadConfig()}
     *
     * @param plugin to run under
     * @param resourceName to get updates from
     * @param toUpdate File to update
     */
    public static void update(Plugin plugin, String resourceName, File toUpdate) {
        update(plugin, resourceName, toUpdate, null);
    }


    /**
     * Manually initate the config updater, requires {@link JavaPlugin#saveDefaultConfig()} and {@link JavaPlugin#reloadConfig()}
     *
     * @param plugin to run under
     * @param resourceName to get updates from
     * @param toUpdate File to update
     * @param ignoredSections sections to not update
     */
    @SneakyThrows
    public static void update(Plugin plugin, String resourceName, File toUpdate, List<String> ignoredSections)  {
        Preconditions.checkArgument(toUpdate.exists(), "The toUpdate file doesn't exist!");

        FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(resourceName), StandardCharsets.UTF_8));
        FileConfiguration currentConfig = YamlConfiguration.loadConfiguration(toUpdate);
        Map<String, String> comments = parseComments(plugin, resourceName, defaultConfig);
        Map<String, String> ignoredSectionsValues = parseIgnoredSections(toUpdate, currentConfig, comments, ignoredSections == null ? Collections.emptyList() : ignoredSections);

        // will write updated config file "contents" to a string
        StringWriter writer = new StringWriter();
        write(defaultConfig, currentConfig, new BufferedWriter(writer), comments, ignoredSectionsValues);
        String value = writer.toString(); // config contents

        Path toUpdatePath = toUpdate.toPath();
        if (!value.equals(new String(Files.readAllBytes(toUpdatePath), StandardCharsets.UTF_8))) { // if updated contents are not the same as current file contents, update
            Files.write(toUpdatePath, value.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static void write(FileConfiguration defaultConfig, FileConfiguration currentConfig, BufferedWriter writer, Map<String, String> comments, Map<String, String> ignoredSectionsValues) throws IOException {
        //Used for converting objects to yaml, then cleared
        FileConfiguration parserConfig = new YamlConfiguration();

        keyLoop: for (String fullKey : defaultConfig.getKeys(true)) {
            String indents = KeyBuilder.getIndents(fullKey, SEPARATOR);

            if (ignoredSectionsValues.isEmpty()) {
                writeCommentIfExists(comments, writer, fullKey, indents);
            } else {
                for (Map.Entry<String, String> entry : ignoredSectionsValues.entrySet()) {
                    if (entry.getKey().equals(fullKey)) {
                        writer.write(ignoredSectionsValues.get(fullKey) + "\n");
                        continue keyLoop;
                    } else if (KeyBuilder.isSubKeyOf(entry.getKey(), fullKey, SEPARATOR)) {
                        continue keyLoop;
                    }
                }

                writeCommentIfExists(comments, writer, fullKey, indents);
            }

            Object currentValue = currentConfig.get(fullKey);

            if (currentValue == null)
                currentValue = defaultConfig.get(fullKey);

            String[] splitFullKey = fullKey.split("[" + SEPARATOR + "]");
            String trailingKey = splitFullKey[splitFullKey.length - 1];

            if (currentValue instanceof ConfigurationSection) {
                writer.write(indents + trailingKey + ":");

                if (!((ConfigurationSection) currentValue).getKeys(false).isEmpty())
                    writer.write("\n");
                else
                    writer.write(" {}\n");

                continue;
            }

            parserConfig.set(trailingKey, currentValue);
            String yaml = parserConfig.saveToString();
            yaml = yaml.substring(0, yaml.length() - 1).replace("\n", "\n" + indents);
            String toWrite = indents + yaml + "\n";
            parserConfig.set(trailingKey, null);
            writer.write(toWrite);
        }

        String danglingComments = comments.get(null);

        if (danglingComments != null)
            writer.write(danglingComments);

        writer.close();
    }

    //Returns a map of key comment pairs. If a key doesn't have any comments it won't be included in the map.
    private static Map<String, String> parseComments(Plugin plugin, String resourceName, FileConfiguration defaultConfig) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource(resourceName)));
        Map<String, String> comments = new LinkedHashMap<>();
        StringBuilder commentBuilder = new StringBuilder();
        KeyBuilder keyBuilder = new ConfigUpdater.KeyBuilder(defaultConfig, SEPARATOR);

        String line;
        while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();

            //Only getting comments for keys. A list/array element comment(s) not supported
            if (trimmedLine.startsWith("-")) {
                continue;
            }

            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {//Is blank line or is comment
                commentBuilder.append(trimmedLine).append("\n");
            } else {//is a valid yaml key
                keyBuilder.parseLine(trimmedLine);
                String key = keyBuilder.toString();

                //If there is a comment associated with the key it is added to comments map and the commentBuilder is reset
                if (commentBuilder.length() > 0) {
                    comments.put(key, commentBuilder.toString());
                    commentBuilder.setLength(0);
                }

                //Remove the last key from keyBuilder if current path isn't a config section or if it is empty to prepare for the next key
                if (!keyBuilder.isConfigSectionWithKeys()) {
                    keyBuilder.removeLastKey();
                }
            }
        }

        reader.close();

        if (commentBuilder.length() > 0)
            comments.put(null, commentBuilder.toString());

        return comments;
    }

    private static Map<String, String> parseIgnoredSections(File toUpdate, FileConfiguration currentConfig, Map<String, String> comments, List<String> ignoredSections) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(toUpdate));
        Map<String, String> ignoredSectionsValues = new LinkedHashMap<>(ignoredSections.size());
        KeyBuilder keyBuilder = new KeyBuilder(currentConfig, SEPARATOR);
        StringBuilder valueBuilder = new StringBuilder();

        String currentIgnoredSection = null;
        String line;
        lineLoop : while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();

            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#"))
                continue;

            if (trimmedLine.startsWith("-")) {
                for (String ignoredSection : ignoredSections) {
                    boolean isIgnoredParent = ignoredSection.equals(keyBuilder.toString());

                    if (isIgnoredParent || keyBuilder.isSubKeyOf(ignoredSection)) {
                        valueBuilder.append("\n").append(line);
                        continue lineLoop;
                    }
                }
            }

            keyBuilder.parseLine(trimmedLine);
            String fullKey = keyBuilder.toString();

            //If building the value for an ignored section and this line is no longer a part of the ignored section,
            //  write the valueBuilder, reset it, and set the current ignored section to null
            if (currentIgnoredSection != null && !KeyBuilder.isSubKeyOf(currentIgnoredSection, fullKey, SEPARATOR)) {
                ignoredSectionsValues.put(currentIgnoredSection, valueBuilder.toString());
                valueBuilder.setLength(0);
                currentIgnoredSection = null;
            }

            for (String ignoredSection : ignoredSections) {
                boolean isIgnoredParent = ignoredSection.equals(fullKey);

                if (isIgnoredParent || keyBuilder.isSubKeyOf(ignoredSection)) {
                    if (valueBuilder.length() > 0)
                        valueBuilder.append("\n");

                    String comment = comments.get(fullKey);

                    if (comment != null) {
                        String indents = KeyBuilder.getIndents(fullKey, SEPARATOR);
                        valueBuilder.append(indents).append(comment.replace("\n", "\n" + indents));//Should end with new line (\n)
                        valueBuilder.setLength(valueBuilder.length() - indents.length());//Get rid of trailing \n and spaces
                    }

                    valueBuilder.append(line);

                    //Set the current ignored section for future iterations of while loop
                    //Don't set currentIgnoredSection to any ignoredSection sub-keys
                    if (isIgnoredParent)
                        currentIgnoredSection = fullKey;

                    break;
                }
            }
        }

        reader.close();

        if (valueBuilder.length() > 0)
            ignoredSectionsValues.put(currentIgnoredSection, valueBuilder.toString());

        return ignoredSectionsValues;
    }

    private static void writeCommentIfExists(Map<String, String> comments, BufferedWriter writer, String fullKey, String indents) throws IOException {
        String comment = comments.get(fullKey);

        //Comments always end with new line (\n)
        if (comment != null)
            //Replaces all '\n' with '\n' + indents except for the last one
            writer.write(indents + comment.substring(0, comment.length() - 1).replace("\n", "\n" + indents) + "\n");
    }

    //Input: 'key1.key2' Result: 'key1'
    private static void removeLastKey(StringBuilder keyBuilder) {
        if (keyBuilder.length() == 0)
            return;

        String keyString = keyBuilder.toString();
        //Must be enclosed in brackets in case a regex special character is the separator
        String[] split = keyString.split("[" + SEPARATOR + "]");
        //Makes sure begin index isn't < 0 (error). Occurs when there is only one key in the path
        int minIndex = Math.max(0, keyBuilder.length() - split[split.length - 1].length() - 1);
        keyBuilder.replace(minIndex, keyBuilder.length(), "");
    }

    private static void appendNewLine(StringBuilder builder) {
        if (builder.length() > 0)
            builder.append("\n");
    }

    public static class KeyBuilder implements Cloneable {

        private final FileConfiguration config;
        private final char separator;
        private final StringBuilder builder;

        public KeyBuilder(FileConfiguration config, char separator) {
            this.config = config;
            this.separator = separator;
            this.builder = new StringBuilder();
        }

        private KeyBuilder(KeyBuilder keyBuilder) {
            this.config = keyBuilder.config;
            this.separator = keyBuilder.separator;
            this.builder = new StringBuilder(keyBuilder.toString());
        }

        public void parseLine(String line) {
            line = line.trim();
            String[] currentSplitLine = line.split(":");
            String key = currentSplitLine[0].replace("'", "").replace("\"", "");

            //Checks keyBuilder path against config to see if the path is valid.
            //If the path doesn't exist in the config it keeps removing last key in keyBuilder.
            while (builder.length() > 0 && !config.contains(builder.toString() + separator + key)) {
                removeLastKey();
            }

            //Add the separator if there is already a key inside keyBuilder
            //If currentSplitLine[0] is 'key2' and keyBuilder contains 'key1' the result will be 'key1.' if '.' is the separator
            if (builder.length() > 0)
                builder.append(separator);

            //Appends the current key to keyBuilder
            //If keyBuilder is 'key1.' and currentSplitLine[0] is 'key2' the resulting keyBuilder will be 'key1.key2' if separator is '.'
            builder.append(key);
        }

        public String getLastKey() {
            if (builder.length() == 0)
                return "";

            return builder.toString().split("[" + separator + "]")[0];
        }

        public boolean isEmpty() {
            return builder.length() == 0;
        }

        //Checks to see if the full key path represented by this instance is a sub-key of the key parameter
        public boolean isSubKeyOf(String parentKey) {
            return isSubKeyOf(parentKey, builder.toString(), separator);
        }

        //Checks to see if subKey is a sub-key of the key path this instance represents
        public boolean isSubKey(String subKey) {
            return isSubKeyOf(builder.toString(), subKey, separator);
        }

        public static boolean isSubKeyOf(String parentKey, String subKey, char separator) {
            if (parentKey.isEmpty())
                return false;

            return subKey.startsWith(parentKey)
                    && subKey.substring(parentKey.length()).startsWith(String.valueOf(separator));
        }

        public static String getIndents(String key, char separator) {
            String[] splitKey = key.split("[" + separator + "]");
            StringBuilder builder = new StringBuilder();

            for (int i = 1; i < splitKey.length; i++) {
                builder.append("  ");
            }

            return builder.toString();
        }

        public boolean isConfigSection() {
            String key = builder.toString();
            return config.isConfigurationSection(key);
        }

        public boolean isConfigSectionWithKeys() {
            String key = builder.toString();
            return config.isConfigurationSection(key) && !config.getConfigurationSection(key).getKeys(false).isEmpty();
        }

        //Input: 'key1.key2' Result: 'key1'
        public void removeLastKey() {
            if (builder.length() == 0)
                return;

            String keyString = builder.toString();
            //Must be enclosed in brackets in case a regex special character is the separator
            String[] split = keyString.split("[" + separator + "]");
            //Makes sure begin index isn't < 0 (error). Occurs when there is only one key in the path
            int minIndex = Math.max(0, builder.length() - split[split.length - 1].length() - 1);
            builder.replace(minIndex, builder.length(), "");
        }

        @Override
        public String toString() {
            return builder.toString();
        }

        @Override
        protected KeyBuilder clone() {
            return new KeyBuilder(this);
        }
    }

    }
