package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableMap;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiFunction;

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

    <T extends ItemModifier<D>, D>ItemBuilder add(@Nullable String id, D data,Class<T> clazz);

    Optional<Object> getData(String id);


    ItemStack build();

}