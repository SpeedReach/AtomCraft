package net.brian.atomcraft.items;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.brian.atomcraft.api.ConfiguredItem;
import net.brian.atomcraft.api.data.ItemJsonData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitConfiguredItem implements ConfiguredItem{

    @Getter
    final String id;

    @Getter
    final Material material;

    @Getter
    final int modelData;

    @Getter
    final ImmutableList<String> rawLore;

    @Getter
    final ImmutableMap<String,Double> flatPlayerStats;
    @Getter
    final ImmutableMap<String,Double> relativePlayerStats;

    @Getter
    final ImmutableMap<String,Object> data;

    public BukkitConfiguredItem(ConfigurationSection config){
        this.id = config.getString("id","");
        this.material = Material.valueOf(config.getString("material",Material.STONE.name()));
        this.modelData = config.getInt("model-data",0);
        this.rawLore = ImmutableList.copyOf(config.getStringList("lore"));
        this.flatPlayerStats = ImmutableMap.copyOf((HashMap<String, Double>) config.get("flat-player-stats"));
        this.relativePlayerStats = ImmutableMap.copyOf((HashMap<String, Double>) config.get("relative-player-stats"));
        this.data = ImmutableMap.copyOf((HashMap<String, Object>) config.get("data"));
    }

    public BukkitConfiguredItem(String id, Material material, int modelData, List<String> rawLore, Map<String, Double> flatPlayerStats, Map<String, Double> relativePlayerStats, Map<String, Object> data) {
        this.id = id;
        this.material = material;
        this.modelData = modelData;
        this.rawLore = ImmutableList.copyOf(rawLore);
        this.flatPlayerStats = ImmutableMap.copyOf(flatPlayerStats);
        this.relativePlayerStats = ImmutableMap.copyOf(relativePlayerStats);
        this.data = ImmutableMap.copyOf(data);
    }

    public static BukkitConfiguredItem empty(String id){
        return new BukkitConfiguredItem(id  ,Material.STONE,0,new ArrayList<>(),Map.of(),Map.of(),Map.of());
    }


}