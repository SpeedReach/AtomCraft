package net.brian.atomcraft.restrictions;

import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemRestriction;
import org.bukkit.entity.Player;

public class SoulBoundRestriction implements ItemRestriction {


    public static final String ID = "soul_bound";

    @Override
    public boolean canUse(Player player, AtomItem atomItem) {
        return atomItem.getData(ID,String.class)
                .map(s -> s.equals(player.getUniqueId().toString()))
                .orElse(true);
    }
}
