package net.brian.atomcraft.items;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.api.services.AtomItemStackBridge;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class PersistentDataItemBridge implements AtomItemStackBridge {

    public final NamespacedKey ATOM_ITEM_KEY;
    private final AtomCraftPlugin plugin;

    public PersistentDataItemBridge(AtomCraftPlugin plugin){
        this.plugin = plugin;
        ATOM_ITEM_KEY = new NamespacedKey(plugin, "atom_item");

    }

    @Override
    public Optional<ItemJsonData> readJson(ItemMeta itemMeta){
        String json = itemMeta.getPersistentDataContainer().get(ATOM_ITEM_KEY, PersistentDataType.STRING);

        if (json == null){
            return Optional.empty();
        }
        ItemJsonData data = plugin.getGsonProvider().getGson().fromJson(json, ItemJsonData.class);
        return Optional.of(data);
    }

    @Override
    public ItemMeta writeJson(ItemMeta itemMeta, ItemJsonData jsonData){
        String data = plugin.getGsonProvider().getGson().toJson(jsonData);
        //System.out.println(data);
        itemMeta.getPersistentDataContainer().set(ATOM_ITEM_KEY,PersistentDataType.STRING,data);
        return itemMeta;
    }

}
