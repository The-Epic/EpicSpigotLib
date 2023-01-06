package me.epic.spigotlib.pdc.datatypes;

import lombok.SneakyThrows;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class FloatArrayDataType implements PersistentDataType<byte[], float[]> {

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<float[]> getComplexType() {
        return float[].class;
    }

    @Override
    @SneakyThrows
    public byte [] toPrimitive(final float[] floats, @NotNull final PersistentDataAdapterContext itemTagAdapterContext) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(); final DataOutputStream dos = new DataOutputStream(bos)) {
            dos.writeInt(floats.length);
            for (final float number : floats) {
                dos.writeFloat(number);
            }
            dos.flush();
            return bos.toByteArray();
        }
    }

    @Override
    @SneakyThrows
    public float [] fromPrimitive(final byte [] bytes, @NotNull final PersistentDataAdapterContext itemTagAdapterContext) {
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes); final DataInputStream dis = new DataInputStream(bis)) {
            final float[] floats = new float[dis.readInt()];
            for (int i = 0; i < floats.length; i++) {
                floats[i] = dis.readFloat();
            }
            return floats;
        }
    }
}