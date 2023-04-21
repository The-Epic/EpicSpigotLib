package me.epic.spigotlib.nms;

import com.mojang.authlib.GameProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface INMSAdapter {

    void setTexture(SkullMeta meta, String texture);
    String getDefaultWorldName();
    void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile);

    ItemMeta addLore(ItemStack item, List<BaseComponent[]> components);
}
