package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemRestriction;
import org.bukkit.entity.Player;



public interface ItemRestrictionService {

        void register(ItemRestriction restriction);

        boolean test(Player player, AtomItem atomItem);

}
