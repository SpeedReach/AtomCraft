package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.*;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.data.ItemModifierDataPair;
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
        id = configuredItem.getId();
        material = configuredItem.getMaterial();
        modelData = configuredItem.getModelData();
        rawLore = configuredItem.getRawLore();

        flatPlayerStats = new HashMap<>(configuredItem.getFlatPlayerStats());
        relativePlayerStats = new HashMap<>(configuredItem.getRelativePlayerStats());
        data = new HashMap<>(configuredItem.getData());
        this.modifiers = configuredItem.getModifiers().stream().map((d) -> Map.entry(UUID.randomUUID().toString(),new ItemModifierContainer(true,d.typeInfo().id(),d.data())))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->b,HashMap::new));
    }

    public AtomItemBuilder(AtomItemStack atomItemStack) throws CfgItemNotFoundException {
        ConfiguredItem configuredItem = AtomCraftPlugin.getInstance().getConfigItemRegistry()
                .getItem(atomItemStack.getId()).orElseThrow(()->new CfgItemNotFoundException(atomItemStack.getId()));
        id = configuredItem.getId();
        material = configuredItem.getMaterial();
        modelData = configuredItem.getModelData();
        rawLore = configuredItem.getRawLore();
        flatPlayerStats = new HashMap<>(atomItemStack.getFlatPlayerStats());
        relativePlayerStats = new HashMap<>(atomItemStack.getRelativePlayerStats());
        data = new HashMap<>(atomItemStack.getData());
        this.modifiers = new HashMap<>(atomItemStack.getModifiers().size()+configuredItem.getModifiers().size());

        //Filter out base modifiers, and replace with configured modifiers
        atomItemStack.getModifiers().forEach((key,value)->{
            if(!value.base()){
                modifiers.put(key,value);
            }
        });
        configuredItem.getModifiers().forEach((d) -> {
            modifiers.put(UUID.randomUUID().toString(),new ItemModifierContainer(true,d.typeInfo().id(),d.data()));
        });
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
        modifiers.put(UUID.randomUUID().toString(),new ItemModifierContainer(false,typeInfo.id(),data));
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
        final ItemJsonData cache = new ItemJsonData(id,flatPlayerStats,relativePlayerStats,modifiers,data);
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
