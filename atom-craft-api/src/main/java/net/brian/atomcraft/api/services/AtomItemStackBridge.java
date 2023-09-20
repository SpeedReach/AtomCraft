package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.data.ItemJsonData;
import org.bukkit.inventory.meta.ItemMeta;

public interface AtomItemStackBridge {

    ItemJsonData readJson(ItemMeta itemMeta);

    ItemMeta writeJson(ItemMeta itemMeta,ItemJsonData jsonData);

}
