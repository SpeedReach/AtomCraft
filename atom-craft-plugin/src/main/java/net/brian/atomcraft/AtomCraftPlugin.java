package net.brian.atomcraft;

import lombok.Getter;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.data.ItemJsonData;
import net.brian.atomcraft.api.services.*;
import net.brian.atomcraft.items.ItemJsonDeserializer;
import org.bukkit.plugin.java.JavaPlugin;

public class AtomCraftPlugin extends JavaPlugin implements AtomCraft {

    @Getter
    public static AtomCraftPlugin instance;


    @Getter
    private ItemModifierRegistry modifierRegistry;

    @Getter
    private ItemDataRegistry dataRegistry;

    @Getter
    private PlayerStatsImplBridge statsImplBridge;

    @Getter
    private ConfigItemRegistry configItemRegistry;

    @Getter
    private GsonProvider gsonProvider;

    @Getter
    private AtomItemStackBridge itemStackBridge;

    @Override
    public void onEnable() {
        instance = this;
        modifierRegistry = new ItemModifierRegistryImpl(this);
        gsonProvider = new net.brian.atomcraft.json.GsonProvider();

        gsonProvider.registerTypeAdapter(ItemJsonData.class,new ItemJsonDeserializer(this));

    }

}