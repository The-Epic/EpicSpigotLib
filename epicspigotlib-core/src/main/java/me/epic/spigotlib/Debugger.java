package me.epic.spigotlib;

import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.logging.Logger;

public class Debugger {

    private static UUID epic = UUID.fromString("9b4fcf9c-e226-4433-988b-b75068f33f21");
    private static Player EPIC = (Player) Bukkit.getOfflinePlayer(epic);
    private static Logger LOGGER = Bukkit.getLogger();

    public static void toEpic(String toTell) {
        if (!EPIC.isOnline()) {
            LOGGER.info(toTell);
        } else {
            EPIC.sendMessage(toTell);
        }
    }

    public static void toEpic(Object obj) {
        if (!EPIC.isOnline()) {
            LOGGER.info(obj.getClass().getSimpleName() + ": " + obj);
        } else {
            EPIC.sendMessage(obj.getClass().getSimpleName() + ": " + obj);
        }
    }

    public static void toEpic(Object... objs) {
        if (!EPIC.isOnline()) {
            LOGGER.info(objs.getClass().getSimpleName() + "[" + objs.length);
            for (Object object : objs) {
                if (object == null) continue;
                LOGGER.info("-" + object);
            }
        } else {
            EPIC.sendMessage(objs.getClass().getSimpleName() + "[" + objs.length);
            for (Object object : objs) {
                if (object == null) continue;
                EPIC.sendMessage("-" + object);
            }
        }
    }


    public static void toEpic(ItemStack... items) {
        if (!EPIC.isOnline()) {
            Bukkit.broadcastMessage("Epic not online, broadcasting");
            Bukkit.broadcastMessage("ItemStacks[" + items.length + "]");
            for (ItemStack item : items) {
                if (item == null) continue;
                Bukkit.broadcastMessage(" - " + item);
            }
        } else {
            Bukkit.broadcastMessage("ItemStacks[" + items.length + "]");
            for (ItemStack item : items) {
                if (item == null) continue;
                Bukkit.broadcastMessage(" - " + item);
            }
        }
    }


    public static void toBroadcast(String toBroadcast) {
        Bukkit.broadcastMessage(toBroadcast);
    }

    public static void toConsole(String toConsole) {
        LOGGER.info(Formatting.translate("<#733bfb>[Epic SpigotLib] Debug: ") + toConsole);
    }
}
