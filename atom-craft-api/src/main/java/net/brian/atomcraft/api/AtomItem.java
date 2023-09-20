package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.data.ItemModifierData;

import java.util.HashMap;
import java.util.Optional;

public interface AtomItem {

    String getId();
    ImmutableMap<String, Double> getFlatPlayerStats();
    ImmutableMap<String, Double> getRelativePlayerStats();
    ImmutableMap<String, ItemModifierData> getModifiers();
    ImmutableMap<String, Object> getData();

    ItemBuilder toBuilder();

    double getFlatPlayerStat(String id);
    double getRelativePlayerStat(String id);
    <T extends  ItemModifierData> Optional<T> getModifier(String id, Class<T> clazz);
    <T> Optional<T> getData(String id,Class<T> clazz);
}
