package me.epic.spigotlib.items;

import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

	/**
	 * Reduces durability of an item
	 *
	 * @param entity For the sound playing
	 * @param item To remove damage from
	 * @param slot To reduce durability from
	 */
	public static void reduceDurability(LivingEntity entity, ItemStack item, EquipmentSlot slot) {
		ItemMeta meta = item.getItemMeta();

		if (meta instanceof Damageable damagable) {
			damagable.setDamage(damagable.getDamage() + 1);

			if (damagable.getDamage() > item.getType().getMaxDurability()) {
				switch (slot) {
				case HEAD -> entity.playEffect(EntityEffect.BREAK_EQUIPMENT_HELMET);
				case CHEST -> entity.playEffect(EntityEffect.BREAK_EQUIPMENT_CHESTPLATE);
				case FEET -> entity.playEffect(EntityEffect.BREAK_EQUIPMENT_BOOTS);
				case HAND -> entity.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
				case LEGS -> entity.playEffect(EntityEffect.BREAK_EQUIPMENT_LEGGINGS);
				case OFF_HAND -> entity.playEffect(EntityEffect.BREAK_EQUIPMENT_OFF_HAND);
				}
				entity.getEquipment().setItem(slot, null);
				return;
			}
			item.setItemMeta(damagable);
		}
	}
}
