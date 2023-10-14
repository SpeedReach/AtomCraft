package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.models.json.ItemJsonData;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

public interface AtomItemStackBridge {

    Optional<ItemJsonData> readJson(ItemMeta itemMeta);

    ItemMeta writeJson(ItemMeta itemMeta,ItemJsonData jsonData);

}
