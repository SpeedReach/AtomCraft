package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.data.ItemModifierData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

public class AtomItemBuilder implements ItemBuilder {


    final Material material;
    final int modelData;
    final List<String> rawLore;
    final HashMap<String, Double> flatPlayerStats;
    final HashMap<String, Double> relativePlayerStats;
    final HashMap<String, ItemModifierData> modifiers;
    final HashMap<String, Object> data;


    public AtomItemBuilder(ConfiguredItem configuredItem){
        material = configuredItem.material();
        modelData = configuredItem.modelData();
        rawLore = configuredItem.rawLore();
        ItemJsonData itemJsonData = configuredItem.jsonData();
        flatPlayerStats = new HashMap<>(itemJsonData.flatPlayerStats());
        relativePlayerStats = new HashMap<>(itemJsonData.relativePlayerStats());
        modifiers = new HashMap<>(itemJsonData.modifiers());
        data = new HashMap<>(itemJsonData.data());
    }

    public AtomItemBuilder(Material material, List<String> rawLore, int modelData,ItemJsonData jsonData){
        this.material = material;
        this.modelData = modelData;
        this.rawLore = rawLore;
        this.flatPlayerStats = jsonData.flatPlayerStats();
        this.relativePlayerStats = jsonData.relativePlayerStats();
        this.modifiers = jsonData.modifiers();
        this.data = jsonData.data();
    }


    @Override
    public ImmutableMap<String, Object> getData() {
        return ImmutableMap.copyOf(data);
    }

    @Override
    public ImmutableMap<String, ItemModifierData> getModifiers() {
        return ImmutableMap.copyOf(modifiers);
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
    public double computeFlatPlayerStat(String id, BiFunction<String,Double,Double> function) {
        return flatPlayerStats.compute(id,function);
    }

    @Override
    public double computeRelativePlayerStat(String id, BiFunction<String,Double,Double> function) {
        return relativePlayerStats.compute(id,function);
    }

    @Override
    public Optional<Double> getFlatPlayerStat(String id) {
        return Optional.ofNullable(flatPlayerStats.get(id));
    }

    @Override
    public Optional<Double> getRelativePlayerStat(String id) {
        return Optional.ofNullable(relativePlayerStats.get(id));
    }

    @Override
    public ItemBuilder setFlatPlayerStats(String id, double value) {
        flatPlayerStats.put(id,value);
        return this;
    }

    @Override
    public ItemBuilder setRelativePlayerStats(String id, double value) {
        relativePlayerStats.put(id,value);
        return this;
    }

    @Override
    public ItemBuilder setData(String id, Object data) {
        this.data.put(id,data);
        return this;
    }

    @Override
    public ItemBuilder add(ItemModifierData data) {
        return null;
    }

    @Override
    public Optional<Object> getData(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(modelData);

        return null;
    }
}
