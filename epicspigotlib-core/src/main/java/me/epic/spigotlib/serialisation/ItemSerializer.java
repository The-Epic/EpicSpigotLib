package me.epic.spigotlib.serialisation;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Base64;

public class ItemSerializer {
    /**
     * Turns an ItemStack into a Base64 String
     *
     * @param itemStack ItemStack
     * @return ItemStack as Base64 String
     * @throws IOException exception
     */
    public static String toBase64(final ItemStack itemStack) throws IOException {
        return Base64.getEncoder().encodeToString(toBytes(itemStack));
    }

    /**
     * Turns an ItemStack into a byte array
     *
     * @param itemStack ItemStack
     * @return ItemStack as byte array
     * @throws IOException exception
     */
    public static byte[] toBytes(final ItemStack itemStack) throws IOException {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); final ObjectOutput objectOutputStream = new BukkitObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(itemStack);
            return outputStream.toByteArray();
        }
    }

    /**
     * Turns a Base64 String into an ItemStack
     *
     * @param input Base64 String
     * @return ItemStack
     */
    public static ItemStack fromBase64(final String input) {
        return fromBytes(Base64.getDecoder().decode(input));
    }

    /**
     * Turns a byte array into an ItemStack
     *
     * @param input byte array
     * @return ItemStack
     */
    @SneakyThrows
    public static ItemStack fromBytes(final byte[] input) {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(input); final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) objectInputStream.readObject();
        }
    }

    /**
     * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is the armor.
     *
     * @param playerInventory to turn into an array of strings.
     * @return Array of strings: [ main content, armor content ]
     * @throws IllegalStateException exception
     */
    public static String[] playerInventoryToBase64(final PlayerInventory playerInventory) {
        //get the main content part, this doesn't return the armor
        final String content = toBase64(playerInventory);
        final String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[]{content, armor};
    }

    /**
     * A method to serialize an inventory to Base64 string.
     * <p>
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     * @throws IllegalStateException exception
     */
    public static String toBase64(final Inventory inventory) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); final ObjectOutput dataOutput = new BukkitObjectOutputStream(outputStream)) {

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.flush();
            outputStream.flush();
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * A method to serialize an {@link ItemStack} array to Base64 String.
     * <p>
     * Based off of {@link #toBase64(Inventory)}.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException exception
     */
    public static String itemStackArrayToBase64(final ItemStack[] items) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); final ObjectOutput dataOutput = new BukkitObjectOutputStream(outputStream)) {

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (final ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // Serialize that array
            dataOutput.flush();
            outputStream.flush();
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (final IOException e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     * <p>
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     * @throws IOException exception
     */
    public static Inventory inventoryFromBase64(final String data) throws IOException {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data)); final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            final Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (final ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     * <p>
     * Base off of {@link #fromBase64(String)}.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException exception
     */
    public static ItemStack[] itemStackArrayFromBase64(final String data) throws IOException {
        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data)); final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            final ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (final ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static String itemStackToBase64(final ItemStack item) {
        try {
            @Cleanup final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            @Cleanup final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(item);
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack itemStackFromBase64(String base64) {
        try {
            @Cleanup final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            @Cleanup final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            return (ItemStack) dataInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
