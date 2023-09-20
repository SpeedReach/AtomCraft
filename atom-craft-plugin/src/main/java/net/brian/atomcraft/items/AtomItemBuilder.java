package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.data.ItemModifierData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.brian.atomcraft.api.ConfiguredItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;


public class  AtomItemBuilder implements ItemBuilder {

    final String id;
    final Material material;
    final int modelData;
    final List<String> rawLore;
    final HashMap<String, Double> flatPlayerStats;
    final HashMap<String, Double> relativePlayerStats;
    final HashMap<String, ItemModifierData> modifiers;
    final HashMap<String, Object> data;

    public AtomItemBuilder(ConfiguredItem configuredItem, Map<String,ItemModifierData> modifiers){
        id = configuredItem.getId();
        material = configuredItem.getMaterial();
        modelData = configuredItem.getModelData();
        rawLore = configuredItem.getRawLore();

        flatPlayerStats = new HashMap<>(configuredItem.getFlatPlayerStats());
        relativePlayerStats = new HashMap<>(configuredItem.getRelativePlayerStats());
        this.modifiers = new HashMap<>(modifiers);
        data = new HashMap<>(configuredItem.getData());
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
        modifiers.put(data.getId(),data);
        return this;
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
        ItemJsonData cache = new ItemJsonData(id,flatPlayerStats,relativePlayerStats,modifiers,data);
        modifiers.forEach((modifierKey,modifierData) -> {
            AtomCraftPlugin.getInstance().getModifierRegistry().getModifier(modifierKey).ifPresent(modifier -> {
                modifier.apply(cache,modifierData);
            });
        });
        meta = AtomCraftPlugin.getInstance().getItemStackBridge().writeJson(meta,cache);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
