package me.epic.spigotlib.utils;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import lombok.SneakyThrows;
import me.epic.spigotlib.internal.EpicSpigotLibInternal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerUtils {
    private static Essentials essentialsPlugin = null;

    static {
        Bukkit.getScheduler().runTask(EpicSpigotLibInternal.getPlugin(), () -> {
            if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) essentialsPlugin = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        });
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
            if (user == null) {
                return player.getName();
            } else {
                String nick = user.getNickname();
                return nick == null ? player.getName() : nick;
            }
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
    @SneakyThrows
    public static Player getPlayer(String name, boolean essentials) {
        if (essentials && essentialsPlugin != null) {
            return essentialsPlugin.matchUser(Bukkit.getServer(), null, name, false, false).getBase();
        }
        return Bukkit.getPlayer(name);
    }

    /**
     * Returns the nickname of an offline player.
     *
     * @param player the player to get the nickname of
     * @return the nickname of the player, or their name if they have no nickname
     */
    public static String getOfflineNickname(OfflinePlayer player) {
        return getOfflineNickname(player, true);
    }

    /**
     * Returns the nickname of an offline player.
     *
     * @param player the player to get the nickname of
     * @param essentials whether to use Essentials to get the nickname
     * @return the nickname of the player, or their name if they have no nickname
     */
    public static String getOfflineNickname(OfflinePlayer player, boolean essentials) {
        if (essentials && essentialsPlugin != null) {
            User user = essentialsPlugin.getOfflineUser(player.getName());
            if (user == null) {
                return player.getName();
            } else {
                String nick = user.getNickname();
                return nick == null ? player.getName() : nick;
            }
        }
        return player.getName();
    }

    /**
     * Returns an offline player by name.
     *
     * @param name the name of the player to get
     * @return the player with the given name
     */
    public static OfflinePlayer getOfflinePlayer(String name) {
        return getOfflinePlayer(name, true);
    }

    /**
     * Returns an offline player by name.
     *
     * @param name the name of the player to get
     * @param essentials whether to use Essentials to get the player
     * @return the player with the given name
     */
    @SneakyThrows
    public static OfflinePlayer getOfflinePlayer(String name, boolean essentials) {
        if (essentials && essentialsPlugin != null) {
            return Bukkit.getOfflinePlayer(essentialsPlugin.matchUser(Bukkit.getServer(), null, name, false, false).getUUID());
        }
        return Bukkit.getOfflinePlayer(name);
    }

    /**
     * Get the nearest player around an Entity
     *
     * @param player to check around
     * @return Nearest Player
     */
    public static Player getNearestPlayer(Player player) {
        return LocationUtils.getNearestPlayer(player.getLocation());
    }
}
