package me.epic.spigotlib;

import me.epic.spigotlib.formatting.Formatting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Debugger {

    private static UUID epic = UUID.fromString("9b4fcf9c-e226-4433-988b-b75068f33f21");
    private static Player EPIC = (Player) Bukkit.getOfflinePlayer(epic);

    public static void toEpic(String toTell) {
        if (!EPIC.isOnline()) {
            Bukkit.broadcastMessage("Epic not online, broadcasting");
            Bukkit.broadcastMessage(toTell);
        } else {
            EPIC.sendMessage(toTell);
        }
    }

    public static void toEpic(int toTell) {
        if (!EPIC.isOnline()) {
            Bukkit.broadcastMessage("Epic not online, broadcasting");
            Bukkit.broadcastMessage("int: " + toTell);
        } else {
            EPIC.sendMessage("int: " + toTell);
        }
    }

    public static void toEpic(ItemStack[] items) {
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
        System.out.println(Formatting.translate("<#733bfb>[Epic SpigotLib] Debug: ") + toConsole);
    }
}
