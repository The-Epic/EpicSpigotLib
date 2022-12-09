package me.epic.spigotlib.serialisation;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDSerialization {
    public static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

    public static UUID getUUIDFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
