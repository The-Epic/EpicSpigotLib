package me.epic.spigotlib;

import lombok.Getter;
import lombok.Setter;
import me.epic.spigotlib.nms.NMSManager;
import me.epic.spigotlib.utils.ClassUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicSpigotLib {

    @Getter @Setter
    private static JavaPlugin plugin;

    static {
        plugin = JavaPlugin.getProvidingPlugin(ClassUtils.getCurrentClass(1));
    }

    public static void loadNMS(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        EpicSpigotLib.loadNMS();
    }

    public static void loadNMS() {
        NMSManager.init();
    }
}
