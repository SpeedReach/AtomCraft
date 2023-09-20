package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ConfiguredItem;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.data.ItemModifierData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AtomItemStack implements AtomItem {

    @Getter
    final String id;

    final ItemStack itemStack;
    final HashMap<String, Double> flatPlayerStats;
    final HashMap<String, Double> relativePlayerStats;
    final HashMap<String, ItemModifierData> modifiers;
    final HashMap<String, Object> data;


    public AtomItemStack(ItemStack itemStack, ItemJsonData jsonData){
        this.flatPlayerStats = jsonData.flatPlayerStats();
        this.relativePlayerStats = jsonData.relativePlayerStats();
        this.modifiers = jsonData.modifiers();
        this.data = jsonData.data();
        this.itemStack = itemStack;

        this.id = (String) jsonData.data().getOrDefault("id","");
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
    public ImmutableMap<String, ItemModifierData> getModifiers() {
        return ImmutableMap.copyOf(modifiers);
    }

    @Override
    public ImmutableMap<String, Object> getData() {
        return ImmutableMap.copyOf(data);
    }

    @Override
    public ItemBuilder toBuilder() {
        ItemMeta meta = itemStack.getItemMeta();
        int modelData = 0;
        List<String> rawLore = null;

        if (meta != null) {
            modelData = meta.getCustomModelData();
            rawLore = meta.getLore();
        }
        if(rawLore == null){
            rawLore = new ArrayList<>();
        }

        Optional<ConfiguredItem> configuredItem = AtomCraftPlugin.instance.getConfigItemRegistry().getItem(id);
        return configuredItem.map(item -> new AtomItemBuilder(
                item,
                modifiers
        )).orElseGet(() -> new AtomItemBuilder(
                BukkitConfiguredItem.empty(id),
                modifiers
        ));
    }

    @Override
    public double getFlatPlayerStat(String id) {
        return flatPlayerStats.get(id);
    }

    @Override
    public double getRelativePlayerStat(String id) {
        return relativePlayerStats.get(id);
    }

    @Override
    public <T extends ItemModifierData> Optional<T> getModifier(String id,Class<T> clazz) {
        ItemModifierData modifierData = modifiers.get(id);
        if(clazz.isInstance(modifierData)){
            return Optional.of((T) modifierData);
        }
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> getData(String id, Class<T> clazz) {
        Object data = this.data.get(id);
        if(clazz.isInstance(data)){
            return Optional.of((T) data);
        }
        return Optional.empty();
    }


}
