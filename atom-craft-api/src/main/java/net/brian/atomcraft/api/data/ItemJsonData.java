package net.brian.atomcraft.api.data;

import org.bukkit.entity.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public record ItemJsonData(
        String id,
        HashMap<String, Double> flatPlayerStats,
        HashMap<String, Double> relativePlayerStats,
        HashMap<String, ItemModifierData> modifiers,
        HashMap<String, Object> data
) {


    public static ItemJsonData EMPTY = new ItemJsonData("",new HashMap<>(),new HashMap<>(),new HashMap<>(),new HashMap<>());


}
