package me.epic.spigotlib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A utility class for creating custom inventories with buttons and custom event handlers.
 *
 * <p>Instances of this class can be used to create custom inventories with buttons that perform custom
 * actions when clicked, and to register event handlers for when the inventory is clicked or closed.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CustomInventory inventory = new CustomInventory(holder, 3, "My Inventory");
 * inventory.addButton(4, new ItemStack(Material.DIAMOND), event -> {
 *     event.getWhoClicked().sendMessage("You clicked the diamond!");
 * });
 * inventory.addClickConsumer(event -> {
 *     event.getWhoClicked().sendMessage("You clicked the inventory!");
 * });
 * inventory.addCloseConsumer(event -> {
 *     event.getPlayer().sendMessage("You closed the inventory!");
 * });
 * inventory.open(player);
 * }</pre>
 *
 * <p>This class provides methods for adding and removing buttons from the inventory, adding event
 * handlers for when the inventory is clicked or closed, and adding or removing items from the inventory.</p>
 */
public class CustomInventory {

	private Map<Integer, Consumer<InventoryClickEvent>> buttons = new HashMap<>();
	private List<Consumer<InventoryClickEvent>> clickActions = new ArrayList<>();
	private List<Consumer<InventoryCloseEvent>> closeActions = new ArrayList<>();

	private Inventory inventory;

	/**
	 * Constructs a new custom inventory with the given parameters.
	 *
	 * @param holder the inventory holder that owns this inventory
	 * @param rows the number of rows in the inventory
	 * @param name the name of the inventory
	 */
	public CustomInventory(InventoryHolder holder, int rows, String name) {
		int size = rows * 9;
		this.inventory = Bukkit.createInventory(holder, size, name);
	}
	/**
	 * Constructs a new custom inventory from an existing inventory.
	 *
	 * @param inventory the inventory to use
	 */
	public CustomInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	/**
	 * Adds a button to the inventory with the given slot, item, and action.
	 *
	 * @param slot the slot in the inventory where the button will be placed
	 * @param item the item that will represent the button
	 * @param action the action that will be executed when the button is clicked
	 */
	public void addButton(int slot, ItemStack item, Consumer<InventoryClickEvent> action) {
		this.buttons.put(slot, action);
		this.inventory.setItem(slot, item);
	}

	/**
	 * Removes the button at the given slot from the inventory.
	 *
	 * @param slot the slot where the button to remove is located
	 */
	public void removeButton(int slot) {
		this.buttons.remove(slot);
	}

	/**
	 * Adds an action to execute when the inventory is clicked.
	 *
	 * @param action the action to add
	 */
	public void addClickConsumer(Consumer<InventoryClickEvent> action) {
		this.clickActions.add(action);
	}

	/**
	 * Adds an action to execute when the inventory is closed.
	 *
	 * @param action the action to add
	 */
	public void addCloseConsumer(Consumer<InventoryCloseEvent> action) {
		this.closeActions.add(action);
	}

	/**
	 * Opens the inventory for the given player.
	 *
	 * @param target the player to open the inventory for
	 * @return the inventory view
	 */
	public InventoryView open(HumanEntity target) {
		return target.openInventory(this.inventory);
	}

	/**
	 * Consumes the given click event, executing any attached actions.
	 *
	 * @param event the click event to consume
	 */
	public void consumeClickEvent(InventoryClickEvent event) {
		int slot = event.getRawSlot();

		Consumer<InventoryClickEvent> buttonConsumer = this.buttons.get(slot);

		if (buttonConsumer != null)
			buttonConsumer.accept(event);

		this.clickActions.forEach(consumer -> consumer.accept(event));
	}

	/**
	 * Consumes the given close event, executing any attached actions.
	 *
	 * @param event the close event to consume
	 */
	public void consumeCloseEvent(InventoryCloseEvent event) {
		this.closeActions.forEach(consumer -> consumer.accept(event));
	}

	/**
	 * Adds an item to the inventory.
	 *
	 * @param item the item to add
	 */
	public void addItem(ItemStack item) {
		this.inventory.addItem(item);
	}

	/**
	 * Sets an item in a specific slot of the inventory.
	 *
	 * @param slot the slot to set the item in
	 * @param item the item to set
	 */
	public void setItem(int slot, ItemStack item) {
		this.inventory.setItem(slot, item);
	}

	/**
	 * Returns the underlying {@link Inventory} object from Bukkit.
	 *
	 * @return the Bukkit inventory
	 */
	public Inventory getBukkitInventory() {
		return this.inventory;
	}

	@Override
	public String toString() {
		return "CustomInventory{" +
				"buttons=" + buttons +
				", clickActions=" + clickActions +
				", closeActions=" + closeActions +
				", inventory=" + inventory +
				'}';
	}
}
