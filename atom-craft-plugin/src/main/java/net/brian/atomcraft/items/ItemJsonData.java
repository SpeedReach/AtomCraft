package net.brian.atomcraft.items;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.data.ItemModifierData;
import net.brian.atomcraft.itemdata.RawItemData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public record ItemJsonData(
        HashMap<String, Double> flatPlayerStats,
        HashMap<String, Double> relativePlayerStats,
        HashMap<String, ItemModifierData> modifiers,
        HashMap<String, Object> data
) {

    public static ItemJsonData fromJson(JsonObject jsonObject, JsonDeserializationContext context) throws JsonParseException {
        HashMap<String,Double> flatStats = jsonObject.get("flat_player_stats").getAsJsonObject().asMap()
                .entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue().getAsDouble())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));

        HashMap<String,Double> relativeStats = jsonObject.get("relative_player_stats").getAsJsonObject().asMap()
                .entrySet().stream().map(entry -> Map.entry(entry.getKey(), entry.getValue().getAsDouble())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));

        HashMap<String, ItemModifierData> modifiers = jsonObject.get("modifiers").getAsJsonObject().asMap()
                .entrySet().stream().map(entry -> {
                    JsonObject modifierObject = entry.getValue().getAsJsonObject();
                    String modifierType = modifierObject.get("type").getAsString();
                    Optional<ItemModifierData> data = AtomCraftPlugin.instance.getModifierRegistry().getModifier(modifierType).map(modifier -> context.deserialize(modifierObject.get("data"), modifier.getDataClass()));
                    return Map.entry(entry.getKey(), data.orElse(new RawItemModifierData(entry.getKey(),modifierType, modifierObject)));
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));

        HashMap<String, Object> data = jsonObject.get("data").getAsJsonObject().asMap()
                .entrySet().stream().map(entry -> {
                    JsonObject dataObject = entry.getValue().getAsJsonObject();;
                    Optional<Object> optionalData = AtomCraftPlugin.instance.getDataRegistry().getDataClass(entry.getKey()).map(dataClass -> context.deserialize(dataObject, dataClass));
                    return Map.entry(entry.getKey(), optionalData.orElse(new RawItemData(dataObject)));
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));

        return new ItemJsonData(flatStats,relativeStats,modifiers,data);
    }

}
