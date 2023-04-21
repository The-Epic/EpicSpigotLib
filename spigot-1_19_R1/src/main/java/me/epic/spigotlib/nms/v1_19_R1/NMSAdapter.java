package me.epic.spigotlib.nms.v1_19_R1;

import com.mojang.authlib.GameProfile;
import me.epic.spigotlib.nms.INMSAdapter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class NMSAdapter implements INMSAdapter {

    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";


    @Override
    public void setTexture(SkullMeta meta, String texture) {
        PlayerProfile profile = Bukkit.createProfile(UUID.nameUUIDFromBytes(texture.getBytes()));
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL(TEXTURE_URL + texture));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
    }

    @Override
    public String getDefaultWorldName() {
        return ((CraftServer) Bukkit.getServer()).getServer().getProperties().levelName;
    }

    @Override
    public void setHeadTexture(@NotNull final Block block, @NotNull final GameProfile gameProfile) {
        final ServerLevel world = ((CraftWorld) block.getWorld()).getHandle();
        final BlockPos blockPosition = new BlockPos(block.getX(), block.getY(), block.getZ());
        final SkullBlockEntity skull = (SkullBlockEntity) world.getBlockEntity(blockPosition);
        assert skull != null;
        skull.setOwner(gameProfile);
    }

    @Override
    public ItemMeta addLore(ItemStack item, List<BaseComponent[]> components) {
        net.minecraft.world.item.ItemStack nmsCopy = CraftItemStack.asNMSCopy(item);
        Component[] nmsComponents = new Component[components.size()];
        int i = 0;
        for (BaseComponent[] component : components) {
            nmsComponents[i++] = Component.Serializer.fromJson(ComponentSerializer.toString(component));
        }
        if (!nmsCopy.hasTag()) {
            nmsCopy.setTag(new CompoundTag());
        }
        CompoundTag tag = nmsCopy.getTag();
        CompoundTag displayTag = tag.getCompound("display");
        if (!displayTag.contains("Lore", 9)) {
            displayTag.put("Lore", new ListTag());
        }
        ListTag loreList = displayTag.getList("Lore", 8);
        loreList.clear();
        for (Component component : nmsComponents) {
            String componentJson = Component.Serializer.toJson(component);
            loreList.add(StringTag.valueOf(componentJson));
        }
        displayTag.put("Lore", loreList);
        tag.put("display", displayTag);
        nmsCopy.setTag(tag);
        return CraftItemStack.getItemMeta(nmsCopy);
    }
}
