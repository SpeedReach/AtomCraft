package net.brian.atomcraft.items;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.services.AtomItemStackBridge;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PersistentDataItemBridge implements AtomItemStackBridge {

    public final NamespacedKey ATOM_ITEM_KEY;
    private final AtomCraftPlugin plugin;

    public PersistentDataItemBridge(AtomCraftPlugin plugin){
        this.plugin = plugin;
        ATOM_ITEM_KEY = new NamespacedKey(plugin, "atom_item");

    }

    @Override
    public ItemJsonData readJson(ItemMeta itemMeta){
        String json = itemMeta.getPersistentDataContainer().get(ATOM_ITEM_KEY, PersistentDataType.STRING);
        ItemJsonData data = null;
        if (json == null){
            data = ItemJsonData.EMPTY;
        }
        else{
            data = plugin.getGsonProvider().getGson().fromJson(json, ItemJsonData.class);
        }
        return data;
    }

    @Override
    public ItemMeta writeJson(ItemMeta itemMeta, ItemJsonData jsonData){
        String data = plugin.getGsonProvider().getGson().toJson(jsonData);
        itemMeta.getPersistentDataContainer().set(ATOM_ITEM_KEY,PersistentDataType.STRING,data);
        return itemMeta;
    }


}
