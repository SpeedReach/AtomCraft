package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.exception.CfgItemNotFoundException;
import net.brian.atomcraft.api.models.ConfiguredItem;
import net.brian.atomcraft.api.models.ItemModifierDataPair;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.api.models.json.ItemModifierContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.BiFunction;


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

    public AtomItemBuilder(AtomItem atomItem) throws CfgItemNotFoundException {
        ConfiguredItem configuredItem = AtomCraftPlugin.getInstance().getConfigItemRegistry()
                .getItem(atomItem.getConfigId()).orElseThrow(()->new CfgItemNotFoundException(atomItem.getConfigId()));
        id = configuredItem.configId();
        material = configuredItem.material();
        modelData = configuredItem.modelData();
        rawLore = configuredItem.rawLore();
        flatPlayerStats = new HashMap<>(configuredItem.flatPlayerStats());
        relativePlayerStats = new HashMap<>(configuredItem.relativePlayerStats());
        data = new HashMap<>(configuredItem.data());
        modifiers = new HashMap<>(atomItem.getModifiers());
    }


    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public Map<String, ItemModifierContainer> getModifiers() {
        return modifiers;
    }

    @Override
    public <T> void addModifier(ItemModifier<T> modifier, T data) {
        modifiers.put(UUID.randomUUID().toString(),new ItemModifierContainer(modifier.getTypeInfo().id(),data));
    }

    @Override
    public Map<String, Double> getFlatPlayerStats() {
        return flatPlayerStats;
    }

    @Override
    public Map<String, Double> getRelativePlayerStats() {
        return relativePlayerStats;
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
        AtomCraftPlugin.instance.getLiveItemCache().writeUID(meta,UUID.randomUUID());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
