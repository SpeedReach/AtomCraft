package net.brian.atomcraft.restrictions;

import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemRestriction;
import net.brian.atomcraft.api.services.ItemRestrictionRegistry;
import net.brian.atomcraft.utils.Pair;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AtomItemRestrictionRegistry implements ItemRestrictionRegistry {

    private final List<Pair<String,ItemRestriction>> restrictions;

    public AtomItemRestrictionRegistry(){
        this.restrictions = new ArrayList<>();
    }

    @Override
    public void register(String id, ItemRestriction restriction) {
        restrictions.add(new Pair<>(id,restriction));
    }

    @Override
    public boolean test(Player player, AtomItem atomItem) {
        for (Pair<String,ItemRestriction> pair : restrictions){
            if (pair.second().canUse(player,atomItem))
                return false;
        }
        return true;
    }

}
