package me.epic.spigotlib;

import me.epic.spigotlib.internal.EpicSpigotLibInternal;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class EpicSpigotLib {
    private static final Set<Class<?>> NMS_CLASSES = new HashSet<>();

    public static void initNMS(JavaPlugin javaPlugin) {
        EpicSpigotLibInternal.loadNMS(javaPlugin);
        shadowJarHax();
    }

    private static void shadowJarHax() {
        NMS_CLASSES.add(me.epic.spigotlib.nms.v1_18_R1.NMSAdapter.class);
        NMS_CLASSES.add(me.epic.spigotlib.nms.v1_18_R2.NMSAdapter.class);
        NMS_CLASSES.add(me.epic.spigotlib.nms.v1_19_R1.NMSAdapter.class);
        NMS_CLASSES.add(me.epic.spigotlib.nms.v1_19_R2.NMSAdapter.class);
        NMS_CLASSES.add(me.epic.spigotlib.nms.v1_19_R3.NMSAdapter.class);
        NMS_CLASSES.add(me.epic.spigotlib.nms.v1_20_R1.NMSAdapter.class);
    }
}
