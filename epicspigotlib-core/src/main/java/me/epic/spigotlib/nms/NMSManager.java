package me.epic.spigotlib.nms;

import me.epic.spigotlib.Version;
import me.epic.spigotlib.utils.ClassUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class NMSManager {
    private static final String PACKAGE = "me.epic.spigotlib.nms.";

    private static INMSAdapter adapter;


    public static void init() {
        if (adapter != null) return;
        String version = Version.getServerNMSVersion();
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));

        try {
            adapter = (INMSAdapter) Class.forName(PACKAGE + version + ".NMSAdapter").getDeclaredConstructor()
                    .newInstance();
            plugin.getLogger().log(Level.INFO, "Supported server version detected: {0}", version);
        } catch (ReflectiveOperationException e) {
            plugin.getLogger().log(Level.SEVERE, "Server version \"{0}\" is unsupported! Please check for updates!",
                    version);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public static INMSAdapter getAdapter() {
        if (adapter == null) {
            throw new RuntimeException("The nms adapter of EpicSpigotLib is required to be enabled.");
        }
        return adapter;
    }

}
