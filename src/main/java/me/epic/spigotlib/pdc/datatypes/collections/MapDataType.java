package me.epic.spigotlib.pdc.datatypes.collections;

import me.epic.spigotlib.PDC;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

import static me.epic.spigotlib.utils.NamespacedKeyUtils.getKeyKey;
import static me.epic.spigotlib.utils.NamespacedKeyUtils.getValueKey;


public class MapDataType<M extends Map<K, V>, K, V> implements PersistentDataType<PersistentDataContainer, M> {

    private static final String E_KEY_MUST_NOT_BE_NULL = "Maps stored in a PersistentDataContainer must not contain any null keys.";
    private static final String E_NOT_A_MAP = "Not a map.";
    private static final NamespacedKey KEY_SIZE = getKeyKey("s");


    private final Class<M> mapClazz;
    private final Supplier<? extends M> mapSupplier;
    private final PersistentDataType<?, K> keyDataType;
    private final PersistentDataType<?, V> valueDataType;

    public MapDataType(@NotNull final Supplier<? extends M> supplier,
                       @NotNull final PersistentDataType<?, K> keyDataType,
                       @NotNull final PersistentDataType<?, V> valueDataType) {
        this.mapSupplier = supplier;
        //noinspection unchecked
        this.mapClazz = (Class<M>) supplier.get().getClass();
        this.keyDataType = keyDataType;
        this.valueDataType = valueDataType;
    }

    @NotNull
    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @NotNull
    @Override
    public Class<M> getComplexType() {
        return mapClazz;
    }

    @NotNull
    @Override
    public PersistentDataContainer toPrimitive(@NotNull final M map, @NotNull final PersistentDataAdapterContext context) {
        final PersistentDataContainer pdc = context.newPersistentDataContainer();
        int index = 0;
        final int size = map.size();
        pdc.set(KEY_SIZE, PDC.INTEGER, size);
        for (final K key : map.keySet()) {
            if (key == null) {
                throw new IllegalArgumentException(E_KEY_MUST_NOT_BE_NULL);
            }
            final V value = map.get(key);
            if (value != null) {
                pdc.set(getValueKey(index), valueDataType, value);
            }
            pdc.set(getKeyKey(index++), keyDataType, key);
        }
        return pdc;
    }

    @NotNull
    @Override
    public M fromPrimitive(@NotNull final PersistentDataContainer pdc, @NotNull final PersistentDataAdapterContext context) {
        final M map = mapSupplier.get();
        final Integer size = pdc.get(KEY_SIZE, PDC.INTEGER);
        if (size == null) {
            throw new IllegalArgumentException(E_NOT_A_MAP);
        }
        for (int i = 0; i < size; i++) {
            final K key = pdc.get(getKeyKey(i), keyDataType);
                map.put(key, pdc.get(getValueKey(i), valueDataType));
        }
        return map;
    }
}