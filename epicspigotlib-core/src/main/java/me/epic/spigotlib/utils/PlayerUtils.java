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

    /**
     * Retrieves a player by their name. If Essentials is enabled, this method will prioritize Essentials players.
     *
     * @param name The name of the player to retrieve.
     * @return The Player object for the given name, or null if the player could not be found.
     */
    public static Player getPlayer(String name) {
        return getPlayer(name, true);
    }

    /**
     * Retrieves a player by their name.
     *
     * @param name The name of the player to retrieve.
     * @param essentials Whether to prioritize Essentials players if Essentials is enabled.
     * @return The Player object for the given name, or null if the player could not be found.
     */
    public static Player getPlayer(String name, boolean essentials) {
        if (essentials && essentialsPlugin != null) {
            User user = essentialsPlugin.getUserMap().getUser(name);
            return user.getBase();
        }
        return Bukkit.getPlayer(name);
    }
}
