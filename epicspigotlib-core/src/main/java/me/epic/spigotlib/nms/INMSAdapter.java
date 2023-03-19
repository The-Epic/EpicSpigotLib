package me.epic.spigotlib.nms;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public interface INMSAdapter {

    void setTexture(SkullMeta meta, String texture);
    String getDefaultWorldName();
}
