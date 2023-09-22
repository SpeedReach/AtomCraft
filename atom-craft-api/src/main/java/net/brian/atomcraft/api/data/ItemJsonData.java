package net.brian.atomcraft.api.data;

import net.brian.atomcraft.api.ItemModifierContainer;

import java.util.HashMap;
import java.util.UUID;


public record ItemJsonData(
        String configId,
        HashMap<String, Double> flatPlayerStats,
        HashMap<String, Double> relativePlayerStats,
        HashMap<String, ItemModifierContainer> modifiers,
        HashMap<String, Object> data
) {


    public static ItemJsonData EMPTY = new ItemJsonData("", new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());


}
