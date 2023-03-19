package me.epic.spigotlib.material;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Tag;

/**
 * This class contains sets of different materials used in Minecraft.
 */
public class MaterialSets {

	/**
	 * A set of all edible meats in Minecraft.
	 */
	public static final Set<Material> MEAT = EnumSet.of(Material.RABBIT, Material.COOKED_RABBIT, Material.RABBIT_STEW,
			Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.MUTTON, Material.COOKED_MUTTON, Material.CHICKEN,
			Material.COOKED_CHICKEN, Material.BEEF, Material.COOKED_BEEF, Material.COD, Material.COOKED_COD,
			Material.SALMON, Material.COOKED_SALMON, Material.ROTTEN_FLESH);

	/**
	 * A set of all swords in Minecraft.
	 */
	public static final Set<Material> SWORDS = EnumSet.of(Material.WOODEN_SWORD, Material.STONE_SWORD,
			Material.IRON_SWORD, Material.GOLDEN_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);

	/**
	 * A set of all axes in Minecraft.
	 */
	public static final Set<Material> AXES = EnumSet.of(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
			Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);

	/**
	 * A set of all shovels in Minecraft.
	 */
	public static final Set<Material> SHOVELS = EnumSet.of(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL,
			Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL);

	/**
	 * A set of all pickaxes in Minecraft.
	 */
	public static final Set<Material> PICKAXES = EnumSet.of(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE,
			Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);

	/**
	 * A set of all hoes in Minecraft.
	 */
	public static final Set<Material> HOES = EnumSet.of(Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE,
			Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE);

	/**
	 * A set of all helmets in Minecraft.
	 */
	public static final Set<Material> HELMETS = EnumSet.of(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET,
			Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET,
			Material.TURTLE_HELMET);

	/**
	 * A set of all chestplates in Minecraft.
	 */
	public static final Set<Material> CHESTPLATE = EnumSet.of(Material.LEATHER_CHESTPLATE,
			Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE,
			Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE);

	/**
	 * A set of all leggings in Minecraft.
	 */
	public static final Set<Material> LEGGINGS = EnumSet.of(Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS,
			Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS);

	/**
	 * A set of all boots in Minecraft.
	 */
	public static final Set<Material> BOOTS = EnumSet.of(Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS,
			Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS);

	/**
	 * A set of all slabs in Minecraft.
	 */
	public static final Set<Material> SLABS = Tag.SLABS.getValues();

	/**
	 * A set of all stairs in Minecraft.
	 */
	public static final Set<Material> STAIRS = Tag.STAIRS.getValues();

	/**
	 * A set of all signs in Minecraft.
	 */
	public static final Set<Material> SIGNS = Tag.SIGNS.getValues();


}
