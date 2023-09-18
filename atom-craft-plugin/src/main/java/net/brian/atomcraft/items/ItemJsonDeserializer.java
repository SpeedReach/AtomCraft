package net.brian.atomcraft.items;

import com.google.gson.*;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.data.ItemModifierData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemJsonDeserializer implements JsonDeserializer<ItemJsonData> {


    @Override
    public ItemJsonData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        return ItemJsonData.fromJson(jsonObject,context);
    }
}
