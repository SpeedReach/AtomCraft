package net.brian.atomcraft.restrictions;

import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemRestriction;
import org.bukkit.entity.Player;

public class VanillaLevelRestriction implements ItemRestriction {

    public final static String ID = "vanilla_level_restriction";

    @Override
    public boolean canUse(Player player, AtomItem atomItem) {
        return atomItem.getData(ID,Integer.class)
                .map(i -> i <= player.getLevel())
                .orElse(true);
    }

}
