package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.data.ItemModifierData;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiFunction;

public interface ItemBuilder {

    ImmutableMap<String, Object> getData();

    ImmutableMap<String, ItemModifierData> getModifiers();

    ImmutableMap<String, Double> getFlatPlayerStats();
    ImmutableMap<String, Double> getRelativePlayerStats();

    double computeFlatPlayerStat(String id, BiFunction<String, Double, Double> function);
    double computeRelativePlayerStat(String id, BiFunction<String, Double, Double> function);
    Optional<Double> getFlatPlayerStat(String id);
    Optional<Double> getRelativePlayerStat(String id);

    ItemBuilder setFlatPlayerStats(String id, double value);

    ItemBuilder setRelativePlayerStats(String id, double value);

    ItemBuilder setData(String id, Object data);

    ItemBuilder add(ItemModifierData data);

    Optional<Object> getData(String id);


    ItemStack build();


    record Cache(
            HashMap<String,Double> flatPlayerStats,
            HashMap<String,Double> relativePlayerStats,
            HashMap<String,Object> data,
            HashMap<String,ItemModifierData> modifiers
    ) {


    }
}