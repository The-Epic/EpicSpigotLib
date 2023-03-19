package me.epic.spigotlib.nms;

import lombok.Getter;
import me.epic.spigotlib.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class NMSManager {
    private static final String PACKAGE = "me.epic.spigotlib.nms.";

    @Getter
    private static INMSAdapter adapter;


    static {
        String version = Version.getServerNMSVersion();

        try {
            adapter = (INMSAdapter) Class.forName(PACKAGE + version + ".NMSAdapter").getDeclaredConstructor()
                    .newInstance();
            Bukkit.getLogger().log(Level.INFO, "Supported server version detected: {0}", version);
        } catch (ReflectiveOperationException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Server version \"{0}\" is unsupported! Please check for updates!",
                    version);
        }
    }
}
