package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.exception.CfgItemNotFoundException;

import java.util.Optional;

public interface AtomItem {

    String getId();
    ImmutableMap<String, Double> getFlatPlayerStats();
    ImmutableMap<String, Double> getRelativePlayerStats();
    ImmutableMap<String, ItemModifierContainer> getModifiers();
    ImmutableMap<String, Object> getData();

    ItemBuilder toBuilder() throws CfgItemNotFoundException;

    double getFlatPlayerStat(String id);
    double getRelativePlayerStat(String id);
    <T> Optional<T> getModifier(String id, ItemModifier.TypeInfo<T> typeInfo);
    <T> Optional<T> getData(String id,Class<T> clazz);
}
