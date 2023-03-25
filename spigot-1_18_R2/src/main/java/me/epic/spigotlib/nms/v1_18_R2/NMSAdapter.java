package me.epic.spigotlib.nms.v1_18_R2;

import com.mojang.authlib.GameProfile;
import me.epic.spigotlib.nms.INMSAdapter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
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
}
