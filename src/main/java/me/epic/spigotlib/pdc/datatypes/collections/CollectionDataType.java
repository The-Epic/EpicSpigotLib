package me.epic.spigotlib.pdc.datatypes.collections;

import me.epic.spigotlib.PDT;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;

import static me.epic.spigotlib.utils.NamespacedKeyUtils.getValueKey;

public class CollectionDataType<C extends Collection<T>, T> implements PersistentDataType<PersistentDataContainer, C> {

    private static final String E_NOT_A_COLLECTION = "Not a collection.";
    private static final NamespacedKey KEY_SIZE = getValueKey("s");

    private final Supplier<? extends C> collectionSupplier;
    private final Class<C> collectionClazz;
    private final PersistentDataType<?, T> dataType;

    public CollectionDataType(@NotNull final Supplier<C> supplier,
                              @NotNull final PersistentDataType<?, T> dataType) {
        //noinspection unchecked
        this.collectionClazz = (Class<C>) supplier.get().getClass();
        this.collectionSupplier = supplier;
        this.dataType = dataType;
    }

    @NotNull
    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @NotNull
    @Override
    public Class<C> getComplexType() {
        return collectionClazz;
    }

    @NotNull
    @Override
    public PersistentDataContainer toPrimitive(@NotNull final C collection, @NotNull final PersistentDataAdapterContext context) {
        final PersistentDataContainer pdc = context.newPersistentDataContainer();
        pdc.set(KEY_SIZE, PDT.INTEGER, collection.size());
        int index = 0;
        for (final T data : collection) {
            if (data != null) {
                pdc.set(getValueKey(index), dataType, data);
            }
            index++;
        }
        return pdc;
    }

    @NotNull
    @Override
    public C fromPrimitive(@NotNull final PersistentDataContainer pdc, @NotNull final PersistentDataAdapterContext context) {
        final C collection = (C) collectionSupplier.get();
        final Integer size = pdc.get(KEY_SIZE, PDT.INTEGER);
        if (size == null) {
            throw new IllegalArgumentException(E_NOT_A_COLLECTION);
        }
        for (int i = 0; i < size; i++) {
                collection.add(pdc.get(getValueKey(i), dataType));
        }
        return collection;
    }

}