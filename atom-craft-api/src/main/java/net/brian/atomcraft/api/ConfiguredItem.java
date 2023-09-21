package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;

public interface ConfiguredItem{

    String getId();
    Material getMaterial();
    int getModelData();
    ImmutableList<String> getRawLore();

    ImmutableMap<String, Double> getFlatPlayerStats();
    ImmutableMap<String, Double> getRelativePlayerStats();

    ImmutableMap<String, Object> getData();

    ImmutableList<ConfiguredModifier> getModifiers();

    public record ConfiguredModifier<T>(
            ItemModifier.TypeInfo<T> typeInfo,
            T data
    ){

    }
}
