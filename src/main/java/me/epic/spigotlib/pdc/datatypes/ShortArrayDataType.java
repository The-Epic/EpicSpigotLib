package me.epic.spigotlib.pdc.datatypes;

import lombok.SneakyThrows;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ShortArrayDataType implements PersistentDataType<byte[], short[]> {

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<short[]> getComplexType() {
        return short[].class;
    }

    @Override
    @SneakyThrows
    public byte [] toPrimitive(final short[] shorts, @NotNull final PersistentDataAdapterContext itemTagAdapterContext) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(); final DataOutputStream dos = new DataOutputStream(bos)) {
            dos.writeInt(shorts.length);
            for (final short number : shorts) {
                dos.writeShort(number);
            }
            dos.flush();
            return bos.toByteArray();
        }
    }

    @Override
    @SneakyThrows
    public short [] fromPrimitive(final byte [] bytes, @NotNull final PersistentDataAdapterContext itemTagAdapterContext) {
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes); final DataInputStream dis = new DataInputStream(bis)) {
            final short[] shorts = new short[dis.readInt()];
            for (int i = 0; i < shorts.length; i++) {
                shorts[i] = dis.readShort();
            }
            return shorts;
        }
    }
}