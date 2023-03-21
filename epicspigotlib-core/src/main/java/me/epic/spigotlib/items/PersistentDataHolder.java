package me.epic.spigotlib.items;

import lombok.Builder;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public record  PersistentDataHolder<T, Z>(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {

    public PersistentDataHolder(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public static <T, Z> PersistentDataHolder of(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        return new PersistentDataHolder<>(key, type, value);
    }

    public NamespacedKey key() {
        return key;
    }

    public PersistentDataType<T, Z> type() {
        return type;
    }

    public Z value() {
        return value;
    }
}
