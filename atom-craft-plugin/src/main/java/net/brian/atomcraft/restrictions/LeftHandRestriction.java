package net.brian.atomcraft.restrictions;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.ItemRestriction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;
import java.util.UUID;

public class LeftHandRestriction implements ItemRestriction {

    public static final String ID = "no_left_hand";

    @Override
    public boolean canUse(Player player, AtomItem atomItem) {
        Optional<Boolean> leftHandOnly =  atomItem.getData(ID,boolean.class);
        if(leftHandOnly.isEmpty() || !leftHandOnly.get()){
            return true;
        }
        ItemMeta offHandMeta = player.getInventory().getItemInOffHand().getItemMeta();
        if(offHandMeta == null) return true;

        return !AtomCraftPlugin.getInstance().getLiveItemCache().readUID(offHandMeta).equals(atomItem.getUniqueId());
    }

    public ItemBuilder apply(ItemBuilder itemBuilder){
        itemBuilder.getData().put(ID,true);
        return itemBuilder;
    }

}
