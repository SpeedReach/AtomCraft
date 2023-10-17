package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.models.json.ItemModifierContainer;
import net.brian.atomcraft.api.utils.Pair;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface ItemBuilder {

    Map<String, Object> getData();

    Map<String, ItemModifierContainer> getModifiers();

    <T> void addModifier(ItemModifier<T> modifier, T data);


    Map<String, Double> getFlatPlayerStats();
    Map<String, Double> getRelativePlayerStats();


    ItemStack build();

}