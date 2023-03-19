package me.epic.spigotlib.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.epic.spigotlib.serialisation.ItemSerializer;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ItemStackAdapter extends TypeAdapter<ItemStack> {
    @Override
    public void write(JsonWriter out, ItemStack value) throws IOException {
        out.beginObject();
        out.name("item");
        out.value(ItemSerializer.itemStackToBase64(value));
        out.endObject();
    }

    @Override
    public ItemStack read(JsonReader in) throws IOException {
        JsonObject json = new JsonParser().parse(in).getAsJsonObject();
        if (json.has("item")) {
            return ItemSerializer.itemStackFromBase64(json.get("item").getAsString());
        }

        return null;
    }
}
