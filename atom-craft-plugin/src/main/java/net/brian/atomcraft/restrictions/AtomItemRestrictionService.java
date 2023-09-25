package net.brian.atomcraft.restrictions;

import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemRestriction;
import net.brian.atomcraft.api.services.ItemRestrictionService;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AtomItemRestrictionService implements ItemRestrictionService {

    private final List<ItemRestriction> restrictions;

    public AtomItemRestrictionService(){
        this.restrictions = new ArrayList<>();
    }

    @Override
    public void register(ItemRestriction restriction) {
        restrictions.add(restriction);
    }

    @Override
    public boolean test(Player player, AtomItem atomItem) {
        for (ItemRestriction restriction : restrictions){
            if (restriction.canUse(player,atomItem))
                return false;
        }
        return true;
    }

}
