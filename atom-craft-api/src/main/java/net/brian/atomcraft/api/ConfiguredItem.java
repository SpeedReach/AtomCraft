package net.brian.atomcraft.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.data.ItemModifierData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface ConfiguredItem{

    String getId();
    Material getMaterial();
    int getModelData();
    ImmutableList<String> getRawLore();

    ImmutableMap<String, Double> getFlatPlayerStats();
    ImmutableMap<String, Double> getRelativePlayerStats();

    ImmutableMap<String, Object> getData();

}
