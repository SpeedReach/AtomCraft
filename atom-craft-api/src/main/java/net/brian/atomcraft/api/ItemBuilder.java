package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.utils.Pair;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ItemBuilder {

    ImmutableMap<String, Object> getData();

    ImmutableMap<String, ItemModifierContainer> getModifiers();

    ImmutableMap<String, Double> getFlatPlayerStats();
    ImmutableMap<String, Double> getRelativePlayerStats();

    double computeFlatPlayerStat(String id, BiFunction<String, Double, Double> function);
    double computeRelativePlayerStat(String id, BiFunction<String, Double, Double> function);
    Optional<Double> getFlatPlayerStat(String id);
    Optional<Double> getRelativePlayerStat(String id);

    ItemBuilder setFlatPlayerStats(String id, double value);

    ItemBuilder setRelativePlayerStats(String id, double value);

    ItemBuilder setData(String id, Object data);

    <D> ItemBuilder addModifier(ItemModifier.TypeInfo<D> typeInfo, D data);

    void removeModifier(Function<Pair<String,ItemModifierContainer>, Boolean> function);
    void removeModifier(String id);

    Optional<Object> getData(String id);


    ItemStack build();

}