package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.*;
import net.brian.atomcraft.api.models.ConfiguredItem;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.api.models.json.ItemModifierContainer;
import net.brian.atomcraft.api.models.ItemModifierDataPair;
import net.brian.atomcraft.api.exception.CfgItemNotFoundException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class  AtomItemBuilder implements ItemBuilder {

    final String id;
    final Material material;
    final int modelData;
    final List<String> rawLore;
    final HashMap<String, Double> flatPlayerStats;
    final HashMap<String, Double> relativePlayerStats;
    final HashMap<String, ItemModifierContainer> modifiers;
    final HashMap<String, Object> data;

    public AtomItemBuilder(ConfiguredItem configuredItem)  {
        id = configuredItem.configId();
        material = configuredItem.material();
        modelData = configuredItem.modelData();
        rawLore = configuredItem.rawLore();

        flatPlayerStats = new HashMap<>(configuredItem.flatPlayerStats());
        relativePlayerStats = new HashMap<>(configuredItem.relativePlayerStats());
        data = new HashMap<>(configuredItem.data());
        modifiers = new HashMap<>();
    }

    public AtomItemBuilder(AtomItemStack atomItemStack) throws CfgItemNotFoundException {
        ConfiguredItem configuredItem = AtomCraftPlugin.getInstance().getConfigItemRegistry()
                .getItem(atomItemStack.getConfigId()).orElseThrow(()->new CfgItemNotFoundException(atomItemStack.getConfigId()));
        id = configuredItem.configId();
        material = configuredItem.material();
        modelData = configuredItem.modelData();
        rawLore = configuredItem.rawLore();
        flatPlayerStats = new HashMap<>(atomItemStack.getFlatPlayerStats());
        relativePlayerStats = new HashMap<>(atomItemStack.getRelativePlayerStats());
        data = new HashMap<>(atomItemStack.getData());
        modifiers = new HashMap<>(atomItemStack.getModifiers());
    }

    @Override
    public ImmutableMap<String, Object> getData() {
        return ImmutableMap.copyOf(data);
    }

    @Override
    public ImmutableMap<String, ItemModifierContainer> getModifiers() {
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
    public <D> ItemBuilder addModifier(ItemModifier.TypeInfo<D> typeInfo, D data) {
        modifiers.put(UUID.randomUUID().toString(),new ItemModifierContainer(typeInfo.id(),data));
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
        final ItemJsonData cache = new ItemJsonData(id,UUID.randomUUID().toString(),flatPlayerStats,relativePlayerStats,modifiers,data);
        List<ItemModifierDataPair<Object>> validModifiers = new ArrayList<>();
        modifiers.forEach((modifierKey,modifierData) -> {
            AtomCraftPlugin.getInstance().getModifierRegistry().getModifier(modifierData.type()).ifPresent(modifier -> {
                if(modifier.getTypeInfo().dataClass().isInstance(modifierData.data())){
                    validModifiers.add(new ItemModifierDataPair<>(modifier,modifierData.data()));
                }
            });
        });
        validModifiers.sort(Comparator.comparingInt((pair) -> pair.modifier().getPriority()));
        for(ItemModifierDataPair<Object> pair : validModifiers){
            pair.modifier().apply(cache,pair.data());
        }
        meta = AtomCraftPlugin.getInstance().getItemStackBridge().writeJson(meta,cache);
        meta.setLore(rawLore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
