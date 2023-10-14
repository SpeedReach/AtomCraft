package net.brian.atomcraft.api.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;

public record ConfiguredItem(
        String configId,
        Material material,
        int modelData,
        ImmutableList<String> rawLore,
        ImmutableMap<String, Double> flatPlayerStats,
        ImmutableMap<String, Double> relativePlayerStats,
        ImmutableMap<String, Object> data
){

    public static ConfiguredItem empty(String configId){
        return new ConfiguredItem(configId,Material.STONE,0,ImmutableList.of(),ImmutableMap.of(),ImmutableMap.of(),ImmutableMap.of());
    }

}
