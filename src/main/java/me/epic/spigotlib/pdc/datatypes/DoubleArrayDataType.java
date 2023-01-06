package me.epic.spigotlib.pdc.datatypes;

import lombok.SneakyThrows;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class DoubleArrayDataType implements PersistentDataType<byte[], double[]> {

    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<double[]> getComplexType() {
        return double[].class;
    }

    @Override
    @SneakyThrows
    public byte [] toPrimitive(final double[] doubles, final @NotNull PersistentDataAdapterContext itemTagAdapterContext) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream(); final DataOutputStream dos = new DataOutputStream(bos)) {
            dos.writeInt(doubles.length);
            for (final double number : doubles) {
                dos.writeDouble(number);
            }
            dos.flush();
            return bos.toByteArray();
        }
    }

    @Override
    @SneakyThrows
    public double [] fromPrimitive(final byte [] bytes, @NotNull final PersistentDataAdapterContext itemTagAdapterContext) {
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes); final DataInputStream dis = new DataInputStream(bis)) {
            final double[] doubles = new double[dis.readInt()];
            for (int i = 0; i < doubles.length; i++) {
                doubles[i] = dis.readDouble();
            }
            return doubles;
        }
    }
}