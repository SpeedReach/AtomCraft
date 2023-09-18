package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.data.ItemModifierData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AtomItemStack implements AtomItem {

    final ItemStack itemStack;
    final HashMap<String, Double> flatPlayerStats;
    final HashMap<String, Double> relativePlayerStats;
    final HashMap<String, ItemModifierData> modifiers;
    final HashMap<String, Object> data;


    public AtomItemStack(ItemStack itemStack,ItemJsonData jsonData){
        this.flatPlayerStats = jsonData.flatPlayerStats();
        this.relativePlayerStats = jsonData.relativePlayerStats();
        this.modifiers = jsonData.modifiers();
        this.data = jsonData.data();
        this.itemStack = itemStack;
    }


    public AtomItemStack(ItemStack itemStack, Map<String, Double> flatPlayerStats, Map<String, Double> relativePlayerStats, Map<String, ItemModifierData> modifiers, Map<String, Object> data) {
        this.flatPlayerStats = new  HashMap<>(flatPlayerStats);
        this.relativePlayerStats = new  HashMap<>(relativePlayerStats);
        this.modifiers = new  HashMap<>(modifiers);
        this.data = new  HashMap<>(data);
        this.itemStack = itemStack;
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

        return new AtomItemBuilder(
                itemStack.getType(),
                rawLore,
                modelData,
                new ItemJsonData(
                        flatPlayerStats,
                        relativePlayerStats,
                        modifiers,
                        data
                )
        );
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
