package net.brian.atomcraft.items;

import com.google.gson.*;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.models.json.ItemModifierContainer;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.itemdata.RawItemData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemJsonDeserializer implements JsonDeserializer<ItemJsonData> {

    private final AtomCraft plugin;

    public ItemJsonDeserializer(AtomCraft plugin){
        this.plugin = plugin;
    }


    @Override
    public ItemJsonData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
       if(json.isJsonObject()){
           final JsonObject jsonObject = json.getAsJsonObject();
           return fromJson(jsonObject,context);
       }
       throw new JsonParseException("ItemJsonData must be a JsonObject");
    }

    ItemJsonData fromJson(JsonObject jsonObject,JsonDeserializationContext context) throws JsonParseException {
        JsonElement configElement = jsonObject.get(ItemJsonData.CONFIG_ID_KEY);
        if(configElement == null || !configElement.isJsonPrimitive()){
            throw new JsonParseException("ItemJsonData must have a configId");
        }
        final String id = configElement.getAsString();
        final HashMap<String,Double> flatStats = getStringDoubleHashMap(jsonObject,ItemJsonData.FLAT_PLAYER_STATS_KEY);

        final HashMap<String,Double> relativeStats = getStringDoubleHashMap(jsonObject,ItemJsonData.RELATIVE_PLAYER_STATS_KEY);

        JsonElement modifiersElement = jsonObject.get(ItemJsonData.MODIFIERS_KEY);
        final HashMap<String, ItemModifierContainer> modifiers = !modifiersElement.isJsonObject() ?
                new HashMap<>() : modifiersElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> {
                        JsonObject modifierObject = entry.getValue().getAsJsonObject();
                        String modifierType = modifierObject.get(ItemModifierContainer.TYPE_KEY).getAsString();
                        JsonElement rawData = modifierObject.get(ItemModifierContainer.DATA_KEY);
                        Object data = plugin.getModifierRegistry().getModifier(modifierType)
                                .map(modifier -> context.deserialize(rawData, modifier.getTypeInfo().dataClass()))
                                .orElse(new RawItemData(rawData));
                        return Map.entry(entry.getKey(), new ItemModifierContainer(modifierType, data));
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));


        JsonElement dataElement = jsonObject.get(ItemJsonData.DATA_KEY);
        final HashMap<String, Object> data = dataElement.isJsonObject() ? dataElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> {
                        JsonObject dataObject = entry.getValue().getAsJsonObject();;
                        Optional<Object> optionalData = plugin.getDataRegistry().getDataClass(entry.getKey()).map(dataClass -> context.deserialize(dataObject, dataClass));
                        return Map.entry(entry.getKey(), optionalData.orElse(new RawItemData(dataObject)));
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new)):new HashMap<>();

        return new ItemJsonData(id,flatStats,relativeStats,modifiers,data);
    }


    private static HashMap<String, Double> getStringDoubleHashMap(JsonObject jsonObject,String id) {
        JsonElement element = jsonObject.get(id);
        if (element != null && element.isJsonObject()){
            HashMap<String, Double> stats = new HashMap<>();
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                stats.put(entry.getKey(),entry.getValue().getAsDouble());
            }
            return stats;
        }
        else{
            return new HashMap<>(0);
        }
    }

}
