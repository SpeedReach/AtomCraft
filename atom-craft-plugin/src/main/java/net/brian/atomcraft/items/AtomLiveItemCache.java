package net.brian.atomcraft.items;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.api.services.LiveItemCache;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class AtomLiveItemCache implements LiveItemCache {

    public static final Long CACHE_LIFETIME = 1000L * 60 * 5;//5 minutes

    private final AtomCraftPlugin plugin;
    private final NamespacedKey uidKey;

    private final HashMap<UUID,CacheEntry> cacheMap;

    public AtomLiveItemCache(AtomCraftPlugin plugin){
        this.plugin = plugin;
        this.cacheMap = new HashMap<>();
        this.uidKey = new NamespacedKey(plugin,UID_ITEM_KEY);
    }

    @Override
    public Optional<AtomItem> getItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return Optional.empty();
        Optional<ItemJsonData> optJsonData = plugin.getItemStackBridge().readJson(meta);
        return optJsonData.flatMap(itemJsonData -> readUID(meta).map(uid ->
                cacheMap.compute(uid, (key, entry) -> {
                    if (entry == null) {
                        return new CacheEntry(new AtomItemStack(uid, itemJsonData), System.currentTimeMillis());
                    } else {
                        return new CacheEntry(entry.item(), System.currentTimeMillis());
                    }
                })
        ).map(CacheEntry::item));
    }

    @Override
    public Optional<UUID> readUID(ItemMeta itemMeta) {
        if(itemMeta == null) return Optional.empty();
        String uid = itemMeta.getPersistentDataContainer().get(uidKey, PersistentDataType.STRING);
        if(uid == null) return Optional.empty();
        try {
            return Optional.of(UUID.fromString(uid));
        }
        catch (IllegalArgumentException e){
            return Optional.empty();
        }
    }

    @Override
    public void writeUID(ItemMeta itemMeta, UUID uid) {
        if(itemMeta == null) return;
        itemMeta.getPersistentDataContainer().set(uidKey,PersistentDataType.STRING,uid.toString());
    }


    private void cleanup(){
        long now = System.currentTimeMillis();
        cacheMap.entrySet().removeIf(entry -> now - entry.getValue().lastRead > CACHE_LIFETIME);
    }


    private record CacheEntry(AtomItem item, long lastRead){
    }

}
