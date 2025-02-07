package org.wizard_nightmare.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.Necklace;
import org.wizard_nightmare.game.object.SingaCrystal;
import org.wizard_nightmare.game.object.Weapon;
import org.wizard_nightmare.game.objectContainer.Chest;
import org.wizard_nightmare.game.objectContainer.JewelryBag;
import org.wizard_nightmare.game.objectContainer.RoomSet;

import java.io.IOException;

public class ItemAdapter extends TypeAdapter<Item> {

    // Usamos una instancia de Gson para delegar la serialización/deserialización
    private final Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, Item item) throws IOException {
        // Creamos un objeto JSON en el que incluimos el tipo y los campos comunes de Item
        JsonObject jsonObject = new JsonObject();

        // Incluir el tipo concreto (por ejemplo, "Weapon", "Necklace", etc.)
        jsonObject.addProperty("type", item.getClass().getSimpleName());

        // Serializamos los campos comunes
        // Suponemos que Domain es un enum y lo serializamos usando su nombre
        jsonObject.addProperty("domain", item.getDomain().name());
        // Serializamos el objeto Value (se asume que Value es serializable mediante Gson)
        jsonObject.add("value", gson.toJsonTree(item.getValue()));
        jsonObject.addProperty("viewed", item.isViewed());

        // Si tus subclases tienen campos adicionales y deseas incluirlos,
        // podrías agregar lógica adicional o delegar completamente en Gson.
        gson.toJson(jsonObject, out);
    }

    @Override
    public Item read(JsonReader in) throws IOException {
        JsonObject jsonObject = gson.fromJson(in, JsonObject.class);

        JsonElement typeElement = jsonObject.get("type");
        String type = typeElement != null ? typeElement.getAsString() : null;

        if (type != null) {
            System.out.println(type);
            switch (type) {
                case "Weapon":
                    return gson.fromJson(jsonObject, Weapon.class);
                case "Necklace":
                    return gson.fromJson(jsonObject, Necklace.class);
                case "SingaCrystal":
                    int value = jsonObject.get("value").getAsInt();
                    return SingaCrystal.createCrystal(value);
                case "RoomSet":
                    return gson.fromJson(jsonObject, RoomSet.class);
                case "Chest":
                    return gson.fromJson(jsonObject, Chest.class);
                case "JewelryBag":
                    return gson.fromJson(jsonObject, JewelryBag.class);
                default:
                    throw new IOException("Unknown item type: " + type);
            }
        }

        return null;
    }
}
