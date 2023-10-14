package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.*;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.api.models.json.ItemModifierContainer;
import net.brian.atomcraft.api.exception.CfgItemNotFoundException;
import net.brian.atomcraft.api.exception.NotAtomItemException;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AtomItemStack implements AtomItem {

    @Getter
    final String configId;

    @Getter
    final UUID uniqueId;

    final HashMap<String, Double> flatPlayerStats;
    final HashMap<String, Double> relativePlayerStats;
    final HashMap<String, ItemModifierContainer> modifiers;
    final HashMap<String, Object> data;


    public AtomItemStack(UUID uniqueId,ItemJsonData jsonData) {
        this.uniqueId = uniqueId;
        this.flatPlayerStats = jsonData.flatPlayerStats();
        this.relativePlayerStats = jsonData.relativePlayerStats();
        this.modifiers = jsonData.modifiers();
        this.data = jsonData.data();
        this.configId = jsonData.configId();
    }

    @Override
    public ImmutableMap<String, Double> getFlatPlayerStats() {
        return ImmutableMap.copyOf(flatPlayerStats);
    }

    @Override
    public ImmutableMap<String, Double> getRelativePlayerStats() {
        return ImmutableMap.copyOf(relativePlayerStats);
    }

    @Override
    public ImmutableMap<String, ItemModifierContainer> getModifiers() {
        return ImmutableMap.copyOf(modifiers);
    }

    @Override
    public ImmutableMap<String, Object> getData() {
        return ImmutableMap.copyOf(data);
    }

    @Override
    public ItemBuilder toBuilder() throws CfgItemNotFoundException {
        return new AtomItemBuilder(this);
    }

    @Override
    public double getFlatPlayerStat(String id) {
        return flatPlayerStats.getOrDefault(id,0.0);
    }

    @Override
    public double getRelativePlayerStat(String id) {
        return relativePlayerStats.getOrDefault(id,0.0);
    }

    @Override
    public <T> Optional<T> getModifier(String id,ItemModifier.TypeInfo<T> typeInfo) {
        ItemModifierContainer container = this.modifiers.get(id);
        if (container == null || !Objects.equals(container.type(), typeInfo.id())) {
            return Optional.empty();
        }
        return Optional.of(typeInfo.dataClass().cast(data));
    }


    @Override
    public <T> Optional<T> getData(String id, Class<T> clazz) {
        Object data = this.data.get(id);
        if(clazz.isInstance(data)){
            return Optional.of(clazz.cast(data));
        }
        return Optional.empty();
    }


}
