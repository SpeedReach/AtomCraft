package net.brian.atomcraft.api;

import org.bukkit.entity.Player;

public interface ItemRestriction {

    boolean canUse(Player player,AtomItem atomItem);

}
