package me.epic.spigotlib.utils;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.jetbrains.annotations.NotNull;

public class BlockUtils {

    /**
     * Gets the type of chest
     *
     * @param block - The block to check
     * @return The type of chest
     */
    @NotNull
    public static Chest.Type getChestType(Block block) {
        BlockData blockData = block.getBlockData();

        if(!(blockData instanceof Chest chest)) {
            return null;
        }

        Chest.Type type = chest.getType();
        return type;
    }
}
