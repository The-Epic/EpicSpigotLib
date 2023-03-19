package me.epic.spigotlib.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CustomInventory {

	private Map<Integer, Consumer<InventoryClickEvent>> buttons = new HashMap<>();
	private List<Consumer<InventoryClickEvent>> clickActions = new ArrayList<>();
	private List<Consumer<InventoryCloseEvent>> closeActions = new ArrayList<>();

	private Inventory inventory;

	public CustomInventory(InventoryHolder holder, int rows, String name) {
		int size = rows * 9;
		this.inventory = Bukkit.createInventory(holder, size, name);
	}

	public CustomInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void addButton(int slot, ItemStack item, Consumer<InventoryClickEvent> action) {
		this.buttons.put(slot, action);
		this.inventory.setItem(slot, item);
	}

	public void removeButton(int slot) {
		this.buttons.remove(slot);
	}

	public void addClickConsumer(Consumer<InventoryClickEvent> action) {
		this.clickActions.add(action);
	}

	public void addCloseConsumer(Consumer<InventoryCloseEvent> action) {
		this.closeActions.add(action);
	}

	public InventoryView open(HumanEntity target) {
		return target.openInventory(this.inventory);
	}

	public void consumeClickEvent(InventoryClickEvent event) {
		int slot = event.getRawSlot();

		Consumer<InventoryClickEvent> buttonConsumer = this.buttons.get(slot);

		if (buttonConsumer != null)
			buttonConsumer.accept(event);

		this.clickActions.forEach(consumer -> consumer.accept(event));
	}

	public void consumeCloseEvent(InventoryCloseEvent event) {
		this.closeActions.forEach(consumer -> consumer.accept(event));
	}

	public void addItem(ItemStack item) {
		this.inventory.addItem(item);
	}

	public void setItem(int slot, ItemStack item) {
		this.inventory.setItem(slot, item);
	}
	
	public Inventory getBukkitInventory() {
		return this.inventory;
	}
}
