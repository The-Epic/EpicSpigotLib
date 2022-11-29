package me.epic.spigotlib.inventory;

import java.util.HashMap;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public class CustomInventoryManager implements Listener {
	private final HashMap<InventoryView, CustomInventory> inventoryMap = new HashMap<>();

	public void openGui(HumanEntity player, CustomInventory inventory) {
		this.inventoryMap.put(inventory.open(player), inventory);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory() == null)
			return;

		CustomInventory inventory = this.inventoryMap.get(event.getView());

		if (inventory != null) {
			inventory.consumeClickEvent(event);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onInventoryClose(InventoryCloseEvent event) {
		CustomInventory inventory = inventoryMap.get(event.getView());

		if (inventory != null) {
			inventory.consumeCloseEvent(event);

			this.inventoryMap.remove(event.getView());
		}
	}
}
