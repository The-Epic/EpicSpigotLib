package me.epic.spigotlib;

import me.epic.spigotlib.nms.NMSManager;
import me.epic.spigotlib.utils.ClassUtils;
import me.epic.spigotlib.utils.ServerUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicSpigotLib {

    private static Plugin plugin;

    public static void  loadMockPlugin(Plugin mockPlugin) {
        plugin = mockPlugin;
    }

    public static void loadNMS(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        EpicSpigotLib.loadNMS();
    }

    public static void loadNMS() {
        NMSManager.init();
    }

    public static Plugin getPlugin() {
        if (!ServerUtils.isRunningMockBukkit() && plugin == null) {
            plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
        }
        return plugin;
    }
}
