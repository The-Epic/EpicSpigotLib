package me.epic.spigotlib.serialisation;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDSerialization {

    /**
     * Convert a UUID to a byte array
     *
     * @param uuid to translate
     * @return Byte Array of a UUID
     */
    public static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }


    /**
     * Convert a byte array to uuid
     *
     * @param bytes to transfer
     * @return UUID from byte array
     */
    public static UUID getUUIDFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
