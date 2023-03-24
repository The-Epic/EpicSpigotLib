package me.epic.spigotlib.nms;

import com.mojang.authlib.GameProfile;
import org.bukkit.block.Block;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;


public interface INMSAdapter {

    void setTexture(SkullMeta meta, String texture);
    String getDefaultWorldName();
    void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile);
}
