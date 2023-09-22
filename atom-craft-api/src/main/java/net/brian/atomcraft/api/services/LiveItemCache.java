package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.AtomItem;
import org.bukkit.inventory.ItemStack;

/**
 * Since deserializing a item every time it is used is not efficient,
 * each item has a unique ID that is used to identify it, and is reset everytime it is modified.
 * This registry caches the items in memory so that they can be accessed quickly.
 * Still need to figure out when to drop the cache.
 */
public interface LiveItemCache {

    public AtomItem getItem(ItemStack itemStack);

}
