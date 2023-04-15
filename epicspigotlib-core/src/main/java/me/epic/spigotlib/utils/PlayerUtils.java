package me.epic.spigotlib.utils;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {
    private static Essentials essentialsPlugin = null;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) essentialsPlugin = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
    }

    /**
     * Returns the nickname of the given player.
     *
     * @param player the player whose nickname to get
     * @return the nickname of the player
     */
    public static String getNickname(Player player) {
        return getNickname(player, true);
    }

    /**
     * Gets the nickname of the given player, using Essentials if enabled.
     * @param player The player to get the nickname of.
     * @param essentials Whether to use Essentials for the nickname.
     * @return The player's nickname.
     */
    public static String getNickname(Player player, boolean essentials) {
        if (essentials && essentialsPlugin != null) {
            User user = essentialsPlugin.getUser(player);
            return user.getNickname();
        }
        return player.getCustomName();
    }
}
