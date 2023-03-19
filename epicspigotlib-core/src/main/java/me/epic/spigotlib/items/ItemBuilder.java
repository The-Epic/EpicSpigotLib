package me.epic.spigotlib.items;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

//import com.mojang.authlib.GameProfile;
//import com.mojang.authlib.properties.Property;
//import com.mojang.authlib.properties.PropertyMap;
import me.epic.spigotlib.Version;
import me.epic.spigotlib.formatting.Formatting;
import me.epic.spigotlib.internal.annotations.NMS;
import me.epic.spigotlib.nms.NMSManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
//import org.bukkit.profile.PlayerProfile;
//import org.bukkit.profile.PlayerTextures;

import net.md_5.bungee.api.ChatColor;

public class ItemBuilder {
	private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

	private ItemStack item;
	private ItemMeta meta;

	private ItemBuilder() {
	}

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	public ItemBuilder(Material material, int amount) {
		this.item = new ItemStack(material, amount);
		this.meta = this.item.getItemMeta();
	}

	public static ItemBuilder modifyItem(ItemStack item) {
		ItemBuilder builder = new ItemBuilder();
		builder.item = item;
		builder.meta = item.getItemMeta();

		return builder;
	}

	public ItemBuilder amount(int amount) {
		this.item.setAmount(amount);

		return this;
	}

	public ItemBuilder modifier(Attribute attribute, AttributeModifier modifier) {
		this.meta.addAttributeModifier(attribute, modifier);
		return this;
	}

	public ItemBuilder enchantment(Enchantment enchantment, int level) {
		this.meta.addEnchant(enchantment, level, true);
		return this;
	}

	public ItemBuilder enchantments(Map<Enchantment, Integer> enchantmentAndLevel) {
		for (Map.Entry entry : enchantmentAndLevel.entrySet()) {
			this.meta.addEnchant((Enchantment)entry.getKey(),(Integer) entry.getValue(),true);
		}
		return this;
	}

	public ItemBuilder storedEnchantment(Enchantment enchantment, int level) {
		if (!(this.meta instanceof EnchantmentStorageMeta))
			return this;

		((EnchantmentStorageMeta) this.meta).addStoredEnchant(enchantment, level, true);
		return this;
	}

	public ItemBuilder name(String name) {
		this.meta.setDisplayName(Formatting.translate(name));

		return this;
	}

	public ItemBuilder lore(List<String> lore) {
		List<String> itemLore = getLore();
		lore = lore.stream().map(line -> Formatting.translate(line)).toList();
		itemLore.addAll(lore);

		meta.setLore(itemLore);

		return this;
	}

	public ItemBuilder lore(String... lore) {
		List<String> itemLore = getLore();
		List<String> loreList = Arrays.stream(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line))
				.toList();
		itemLore.addAll(loreList);

		meta.setLore(itemLore);

		return this;
	}

	public ItemBuilder flags(ItemFlag... flags) {
		this.meta.addItemFlags(flags);

		return this;
	}

	public ItemBuilder skullTexture(String texture) {
		if (!(this.meta instanceof SkullMeta skullMeta))
			return this;
		NMSManager.getAdapter().setTexture(skullMeta, texture);
		return this;

	}


	@NMS
	public ItemBuilder skullTexture(Player player) {
		if (!(this.meta instanceof SkullMeta)) return this;
		((SkullMeta)this.meta).setOwningPlayer(player);
		return this;
	}

	public <T, Z> ItemBuilder persistentData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
		this.meta.getPersistentDataContainer().set(key, type, value);
		return this;
	}

	public ItemBuilder modelData(int index) {
		this.meta.setCustomModelData(index);
		return this;
	}

	public ItemBuilder dyeColor(Color color) {
		if (!(this.meta instanceof LeatherArmorMeta))
			return this;

		((LeatherArmorMeta) this.meta).setColor(color);
		return this;
	}

	public ItemBuilder dyeColor(int red, int green, int blue) {
		return dyeColor(Color.fromRGB(red, green, blue));
	}

	public ItemBuilder potionColor(int red, int green, int blue) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).setColor(Color.fromRGB(red, green, blue));
		return this;
	}

	public ItemBuilder potionData(PotionData data) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).setBasePotionData(data);
		return this;
	}

	public ItemBuilder potionEffect(PotionEffect effect) {
		if (!(this.meta instanceof PotionMeta))
			return this;

		((PotionMeta) this.meta).addCustomEffect(effect, true);
		return this;
	}

	public ItemStack build() {
		ItemStack finalItem = this.item;
		finalItem.setItemMeta(this.meta);

		return finalItem;
	}

	private List<String> getLore() {
		return meta.hasLore() ? meta.getLore() : new ArrayList<>();
	}

}
