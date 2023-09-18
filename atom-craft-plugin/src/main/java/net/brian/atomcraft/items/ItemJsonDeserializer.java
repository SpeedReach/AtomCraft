package net.brian.atomcraft.items;

import com.google.gson.*;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.data.ItemModifierData;
import net.brian.atomcraft.itemdata.RawItemData;
import net.brian.atomcraft.itemdata.RawItemModifierData;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemJsonDeserializer implements JsonDeserializer<ItemJsonData> {


    @Override
    public ItemJsonData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
       if(json.isJsonObject()){
           final JsonObject jsonObject = json.getAsJsonObject();
           return fromJson(jsonObject,context);
       }
       return new ItemJsonData(new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>());
    }

    static ItemJsonData fromJson(JsonObject jsonObject,JsonDeserializationContext context){
        HashMap<String,Double> flatStats = getStringDoubleHashMap(jsonObject.get("flat_player_stats"));

        HashMap<String,Double> relativeStats = getStringDoubleHashMap(jsonObject.get("relative_player_stats"));

        JsonElement modifiersElement = jsonObject.get("modifiers");
        HashMap<String, ItemModifierData> modifiers = null;
        if(modifiersElement.isJsonObject()){
            modifiersElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> {
                        JsonObject modifierObject = entry.getValue().getAsJsonObject();
                        String modifierType = modifierObject.get("type").getAsString();
                        Optional<ItemModifierData> data = AtomCraftPlugin.instance.getModifierRegistry().getModifier(modifierType).map(modifier -> context.deserialize(modifierObject.get("data"), modifier.getDataClass()));
                        return Map.entry(entry.getKey(), data.orElse(new RawItemModifierData(entry.getKey(),modifierType, modifierObject)));
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
        }
        else{
            modifiers = new HashMap<>();
        }

        JsonElement dataElement = jsonObject.get("data");
        HashMap<String, Object> data = null;
        if (dataElement.isJsonObject()){
            dataElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> {
                        JsonObject dataObject = entry.getValue().getAsJsonObject();;
                        Optional<Object> optionalData = AtomCraftPlugin.instance.getDataRegistry().getDataClass(entry.getKey()).map(dataClass -> context.deserialize(dataObject, dataClass));
                        return Map.entry(entry.getKey(), optionalData.orElse(new RawItemData(dataObject)));
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
        }
        else{
            data = new HashMap<>();
        }
        return new ItemJsonData(flatStats,relativeStats,modifiers,data);
    }


    private static HashMap<String, Double> getStringDoubleHashMap(JsonElement flatStatsElement) {
        HashMap<String, Double> flatStats;
        if (flatStatsElement.isJsonObject()){
            flatStats=flatStatsElement.getAsJsonObject().asMap()
                    .entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue().getAsDouble())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
        }
        else{
            flatStats = new HashMap<>();
        }
        return flatStats;
    }

}
