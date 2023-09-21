package net.brian.atomcraft.config;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.ConfiguredItem;
import net.brian.atomcraft.api.services.ConfigItemRegistry;

import java.util.HashMap;
import java.util.Optional;

public class FileConfigItemRegistry implements ConfigItemRegistry  {

    private final HashMap<String,ConfiguredItem> items = new HashMap<>();
    private final AtomCraftPlugin plugin;

    public FileConfigItemRegistry(AtomCraftPlugin plugin){
        this.plugin = plugin;
    }



    @Override
    public Optional<ConfiguredItem> getItem(String id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public void register(ConfiguredItem item) {
        items.put(item.getId(),item);
    }
}
