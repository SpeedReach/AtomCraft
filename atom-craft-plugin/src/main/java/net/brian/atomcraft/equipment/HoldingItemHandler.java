package net.brian.atomcraft.equipment;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.services.AtomEquipmentSlot;
import net.brian.atomcraft.api.services.LiveItemCache;
import net.brian.atomcraft.api.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class HoldingItemHandler implements Listener {

    private final AtomCraftPlugin plugin;
    private final LiveItemCache liveItemCache;

    private HoldingItemHandler(AtomCraftPlugin plugin,LiveItemCache liveItemCache){
        this.plugin = plugin;
        this.liveItemCache = liveItemCache;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    // Caches the right and left hand item's uuid of the player, so could be null
    final private HashMap<UUID, Pair<UUID,UUID>> holdingItemCache = new HashMap<>();

    private void run(){
        Bukkit.getScheduler().runTaskTimer(plugin,()->{
            Bukkit.getOnlinePlayers().forEach(this::checkPlayerAndUpdate);
        },0,1);
    }

    private void checkPlayerAndUpdate(Player player){
        holdingItemCache.compute(player.getUniqueId(),(uid,pair)->{
            Optional<AtomItem> mainHand = liveItemCache.getItem(player.getInventory().getItemInMainHand());
            Optional<AtomItem> offHand = liveItemCache.getItem(player.getInventory().getItemInOffHand());
            UUID mainUUID = mainHand.map(AtomItem::getUniqueId).orElse(null);
            UUID offUUID = offHand.map(AtomItem::getUniqueId).orElse(null);
            if(pair == null){
                mainHand.ifPresent(atomItem -> plugin.getStatsImplBridge().equip(player,atomItem, AtomEquipmentSlot.HAND));
                offHand.ifPresent(atomItem -> plugin.getStatsImplBridge().equip(player,atomItem, AtomEquipmentSlot.OFF_HAND));
            }
            else {
                if( pair.first() != mainUUID){
                    mainHand.ifPresent(atomItem -> plugin.getStatsImplBridge().equip(player,atomItem, AtomEquipmentSlot.HAND));
                }
                if(pair.second() != offUUID){
                    offHand.ifPresent(atomItem -> plugin.getStatsImplBridge().equip(player,atomItem, AtomEquipmentSlot.OFF_HAND));
                }
            }
            return new Pair<>(mainUUID,offUUID);
        });
    }

    @EventHandler
    public void removeCacheOnLeave(PlayerQuitEvent event){
        holdingItemCache.remove(event.getPlayer().getUniqueId());
    }


}
