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

public class BukkitConfiguredItem implements ConfiguredItem{

    @Getter
    final String id;

    @Getter
    final int version;

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
        this.version = config.getInt("version",0);
        this.material = Material.valueOf(config.getString("material",Material.STONE.name()));
        this.modelData = config.getInt("model-data",0);
        this.rawLore = ImmutableList.copyOf(config.getStringList("lore"));
        this.flatPlayerStats = ImmutableMap.copyOf((HashMap<String, Double>) config.get("flat-player-stats"));
        this.relativePlayerStats = ImmutableMap.copyOf((HashMap<String, Double>) config.get("relative-player-stats"));
        this.data = ImmutableMap.copyOf((HashMap<String, Object>) config.get("data"));
    }



}
