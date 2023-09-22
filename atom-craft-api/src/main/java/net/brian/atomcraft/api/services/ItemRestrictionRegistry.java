package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemRestriction;
import org.bukkit.entity.Player;

public interface ItemRestrictionRegistry {

        void register(String id, ItemRestriction restriction);

        boolean test(Player player, AtomItem atomItem);

}
