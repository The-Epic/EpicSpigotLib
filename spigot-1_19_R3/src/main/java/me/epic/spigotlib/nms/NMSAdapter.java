package me.epic.spigotlib.nms;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

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
}
