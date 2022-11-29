package me.epic.spigotlib.persistantdata;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class StringSetType implements PersistentDataType<byte[], StringSet> {

	@Override
	public StringSet fromPrimitive(byte[] byteArray, PersistentDataAdapterContext context) {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		StringSet strings = new StringSet();
		try (final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
			final Object obj = objectInputStream.readObject();
			strings = (StringSet) obj;
		} catch (final EOFException ex) {
			System.out.println("End of file reached.");
		} catch (final ClassNotFoundException | IOException ex) {
			ex.printStackTrace();
		}
		return strings;
	}

	@Override
	public Class<StringSet> getComplexType() {
		return StringSet.class;
	}

	@Override
	public Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@Override
	public byte[] toPrimitive(StringSet set, PersistentDataAdapterContext context) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(set);
			oos.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
}
