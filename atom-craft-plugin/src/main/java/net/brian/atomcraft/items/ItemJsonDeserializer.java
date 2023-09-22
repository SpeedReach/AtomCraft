package net.brian.atomcraft.items;

import com.google.gson.*;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.ItemModifierContainer;
import net.brian.atomcraft.api.data.ItemJsonData;
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
       return ItemJsonData.EMPTY;
    }

    ItemJsonData fromJson(JsonObject jsonObject,JsonDeserializationContext context){
        JsonElement jsonElement = jsonObject.get("config_id");
        final String id = jsonElement.isJsonPrimitive() ? jsonElement.getAsString() : "";
        final HashMap<String,Double> flatStats = getStringDoubleHashMap(jsonObject,"flat_player_stats");

        final HashMap<String,Double> relativeStats = getStringDoubleHashMap(jsonObject,"relative_player_stats");

        JsonElement modifiersElement = jsonObject.get("modifiers");
        final HashMap<String, ItemModifierContainer> modifiers = !modifiersElement.isJsonObject() ?
                new HashMap<>() : modifiersElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> {
                        JsonObject modifierObject = entry.getValue().getAsJsonObject();
                        String modifierType = modifierObject.get("type").getAsString();
                        boolean base = modifierObject.get("base").getAsBoolean();
                        JsonElement rawData = modifierObject.get("data");
                        Object data = plugin.getModifierRegistry().getModifier(modifierType)
                                .map(modifier -> context.deserialize(rawData, modifier.getTypeInfo().dataClass()))
                                .orElse(new RawItemData(rawData));
                        return Map.entry(entry.getKey(), new ItemModifierContainer(base,modifierType, data));
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));


        JsonElement dataElement = jsonObject.get("data");
        final HashMap<String, Object> data = !dataElement.isJsonObject() ? new HashMap<>()
                : dataElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> {
                        JsonObject dataObject = entry.getValue().getAsJsonObject();;
                        Optional<Object> optionalData = plugin.getDataRegistry().getDataClass(entry.getKey()).map(dataClass -> context.deserialize(dataObject, dataClass));
                        return Map.entry(entry.getKey(), optionalData.orElse(new RawItemData(dataObject)));
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));

        return new ItemJsonData(id,flatStats,relativeStats,modifiers,data);
    }


    private static HashMap<String, Double> getStringDoubleHashMap(JsonObject jsonObject,String id) {
        JsonElement element = jsonObject.get(id);

        HashMap<String, Double> flatStats;
        if (element != null && element.isJsonObject()){
            flatStats=element.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue().getAsDouble())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
        }
        else{
            flatStats = new HashMap<>();
        }
        return flatStats;
    }

}
