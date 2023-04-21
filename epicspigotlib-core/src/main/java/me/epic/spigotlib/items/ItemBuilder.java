package me.epic.spigotlib.items;

import me.epic.spigotlib.formatting.Formatting;
import me.epic.spigotlib.internal.annotations.NMS;
import me.epic.spigotlib.nms.NMSManager;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A builder class for creating ItemStacks with various attributes and metadata.
 */
public class ItemBuilder {

	private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

	private ItemStack item;
	private ItemMeta meta;

	private ItemBuilder() {
	}

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	/**
	 * Constructs a new ItemBuilder with the given material and amount.
	 *
	 * @param material the material of the item
	 * @param amount the amount of the item
	 */
	public ItemBuilder(Material material, int amount) {
		this.item = new ItemStack(material, amount);
		this.meta = this.item.getItemMeta();
	}

	/**
	 * Returns a new ItemBuilder with an existing ItemStack.
	 *
	 * @param item the existing ItemStack to modify
	 * @return a new ItemBuilder with the given ItemStack
	 */
	public static ItemBuilder modifyItem(ItemStack item) {
		ItemBuilder builder = new ItemBuilder();
		builder.item = item;
		builder.meta = item.getItemMeta();

		return builder;
	}

	/**
	 * Sets the amount of the item.
	 *
	 * @param amount the amount of the item
	 * @return this ItemBuilder
	 */
	public ItemBuilder amount(int amount) {
		this.item.setAmount(amount);

		return this;
	}

	/**
	 * Adds an attribute modifier to the item.
	 *
	 * @param attribute the attribute to modify
	 * @param modifier the modifier to apply
	 * @return this ItemBuilder
	 */
	public ItemBuilder modifier(Attribute attribute, AttributeModifier modifier) {
		this.meta.addAttributeModifier(attribute, modifier);
		return this;
	}

	/**
	 * Adds an enchantment to the item.
	 *
	 * @param enchantment the enchantment to add
	 * @param level the level of the enchantment
	 * @return this ItemBuilder
	 */
	public ItemBuilder enchantment(Enchantment enchantment, int level) {
		this.meta.addEnchant(enchantment, level, true);
		return this;
	}

	/**
	 * Adds multiple enchantments to the item.
	 *
	 * @param enchantmentAndLevel a Map containing the enchantments and their levels
	 * @return this ItemBuilder
	 */
	public ItemBuilder enchantments(Map<Enchantment, Integer> enchantmentAndLevel) {
		for (Map.Entry<Enchantment, Integer> entry : enchantmentAndLevel.entrySet()) {
			this.meta.addEnchant(entry.getKey(), entry.getValue(),true);
		}
		return this;
	}

	/**
	 * Adds a stored enchantment to the item.
	 *
	 * @param enchantment the enchantment to add
	 * @param level the level of the enchantment
	 * @return this ItemBuilder
	 */
	public ItemBuilder storedEnchantment(Enchantment enchantment, int level) {
		if (!(this.meta instanceof EnchantmentStorageMeta))
			return this;

		((EnchantmentStorageMeta) this.meta).addStoredEnchant(enchantment, level, true);
		return this;
	}

	/**
	 * Sets the display name of the item.
	 *
	 * @param name the name to set
	 * @return this ItemBuilder
	 */
	public ItemBuilder name(String name) {
		this.meta.setDisplayName(Formatting.translate(name));

		return this;
	}

	@NMS
	public ItemBuilder loreComponents(BaseComponent[]... components) {
		loreComponents(Arrays.stream(components).toList());
		return this;
	}

	@NMS
	public ItemBuilder loreComponents(List<BaseComponent[]> components) {
		this.meta = NMSManager.getAdapter().addLore(item, components);
		return this;
	}

	/**
	 * Adds a list of lore to the current ItemStack meta.
	 * @param lore The list of lore to add.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder lore(List<String> lore) {
		List<String> itemLore = getLore();
		lore = lore.stream().map(Formatting::translate).toList();
		itemLore.addAll(lore);

		meta.setLore(itemLore);

		return this;
	}

	/**
	 * Adds an array of lore to the current ItemStack meta.
	 * @param lore The array of lore to add.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder lore(String... lore) {
		lore(Arrays.stream(lore).toList());
		return this;
	}

	/**
	 * Adds item flags to the current ItemStack meta.
	 * @param flags The flags to add.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder flags(ItemFlag... flags) {
		this.meta.addItemFlags(flags);

		return this;
	}

	/**
	 * Sets the texture of a SkullMeta item.
	 * @param texture The texture to set.
	 * @return The ItemBuilder instance.
	 * @nms
	 */
	@NMS
	public ItemBuilder skullTexture(String texture) {
		if (!(this.meta instanceof SkullMeta skullMeta))
			return this;
		NMSManager.getAdapter().setTexture(skullMeta, texture);
		return this;

	}

	/**
	 * Sets the owning player of a SkullMeta item.
	 * @param player The owning player to set.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder skullTexture(Player player) {
		if (!(this.meta instanceof SkullMeta)) return this;
		((SkullMeta)this.meta).setOwningPlayer(player);
		return this;
	}

	/**
	 * Sets a persistent data value for the current ItemStack meta.
	 * @param key The key for the data.
	 * @param type The data type.
	 * @param value The data value.
	 * @return The ItemBuilder instance.
	 */
	public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
		this.meta.getPersistentDataContainer().set(key, type, value);
		return this;
	}

	/**
	 * Sets the custom model data of the current ItemStack meta.
	 * @param index The custom model data index to set.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder modelData(int index) {
		this.meta.setCustomModelData(index);
		return this;
	}

	/**
	 * Sets the dye color of a LeatherArmorMeta item.
	 * @param color The color to set.
	 * @return The ItemBuilder instance.
	 */
	public ItemBuilder dyeColor(Color color) {
		if (!(this.meta instanceof LeatherArmorMeta))
			return this;

		((LeatherArmorMeta) this.meta).setColor(color);
		return this;
	}

	/**
	 * Sets the dye color of the item to the specified RGB values.
	 *
	 * @param red the red component of the color (0-255)
	 * @param green the green component of the color (0-255)
	 * @param blue the blue component of the color (0-255)
	 * @return the ItemBuilder instance
	 */
	public ItemBuilder dyeColor(int red, int green, int blue) {
		return dyeColor(Color.fromRGB(red, green, blue));
	}

	/**
	 * Sets the potion color of the item to the specified RGB values.
	 *
	 * @param red   the red component of the color (0-255)
	 * @param green the green component of the color (0-255)
	 * @param blue  the blue component of the color (0-255)
	 * @return      the ItemBuilder instance
	 */
	public ItemBuilder potionColor(int red, int green, int blue) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).setColor(Color.fromRGB(red, green, blue));
		return this;
	}

	/**
	 * Sets the base potion data of the item.
	 *
	 * @param data  the potion data to set
	 * @return      the ItemBuilder instance
	 */
	public ItemBuilder potionData(PotionData data) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).setBasePotionData(data);
		return this;
	}

	/**
	 * Adds a custom potion effect to the item.
	 *
	 * @param effect    the potion effect to add
	 * @return          the ItemBuilder instance
	 */
	public ItemBuilder potionEffect(PotionEffect effect) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).addCustomEffect(effect, true);
		return this;
	}

	/**
	 * Adds multiple custom potion effects to the item.
	 *
	 * @param effects   the potion effects to add
	 * @return          the ItemBuilder instance
	 */
	public ItemBuilder potionEffects(PotionEffect... effects) {
		if (!(this.meta instanceof PotionMeta potionMeta))
			return this;

		for (PotionEffect effect : effects) {
			potionMeta.addCustomEffect(effect, true);
		}

		return this;
	}

	/**
	 * Builds and returns the ItemStack with the current ItemMeta.
	 *
	 * @return  the ItemStack with the current ItemMeta
	 */
	public ItemStack build() {
		ItemStack finalItem = this.item;
		finalItem.setItemMeta(this.meta);

		return finalItem;
	}

	/**
	 * Modifies the ItemMeta of the item using the specified Consumer.
	 *
	 * @param <T> the type of the ItemMeta to modify
	 * @param metaType the Class object representing the type of ItemMeta to modify
	 * @param action the Consumer that modifies the ItemMeta
	 * @return the ItemBuilder instance
	 */
	public <T extends ItemMeta> ItemBuilder modify(Class<T> metaType, Consumer<T> action) {
		if (!metaType.isAssignableFrom(meta.getClass())) {
			return this;
		}

		action.accept(metaType.cast(meta));
		return this;
	}

	private List<String> getLore() {
		return meta.hasLore() ? meta.getLore() : new ArrayList<>();
	}

}
