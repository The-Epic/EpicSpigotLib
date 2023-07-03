package me.epic.spigotlib.internal;

import me.epic.spigotlib.nms.NMSManager;
import me.epic.spigotlib.utils.ClassUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @deprecated for internal use only, use the non-deprecated class
 */
@Deprecated
public class EpicSpigotLibInternal {

    private static Plugin plugin;


    public static void loadNMS(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        EpicSpigotLibInternal.loadNMS();
    }

    public static void loadNMS() {
        NMSManager.init();
    }

    public static Plugin getPlugin() {
        if (plugin == null) {
            plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
        }
        return plugin;
    }
}
