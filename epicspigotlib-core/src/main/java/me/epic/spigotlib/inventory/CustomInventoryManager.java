package me.epic.spigotlib.inventory;

import me.epic.spigotlib.internal.EpicSpigotLibInternal;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CustomInventoryManager implements Listener {
    private final Map<InventoryView, CustomInventory> inventoryMap = new HashMap<>();

    public CustomInventoryManager(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public CustomInventoryManager() {
        this(EpicSpigotLibInternal.getPlugin());
    }

    public void openInventory(HumanEntity player, CustomInventory inventory) {
        this.inventoryMap.put(inventory.open(player), inventory);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;

        CustomInventory inventory = inventoryMap.get(event.getView());

        if (inventory != null) {
            inventory.consumeClickEvent(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        CustomInventory inventory = inventoryMap.remove(event.getView());

        if (inventory != null) {
            inventory.consumeCloseEvent(event);
        }
    }
}