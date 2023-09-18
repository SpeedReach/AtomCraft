package net.brian.atomcraft;

import lombok.Getter;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.services.ConfigItemRegistry;
import net.brian.atomcraft.api.services.ItemDataRegistry;
import net.brian.atomcraft.api.services.ItemModifierRegistry;
import net.brian.atomcraft.api.services.PlayerStatsImplBridge;
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

    @Override
    public void onEnable() {
        instance = this;
        modifierRegistry = new ItemModifierRegistryImpl(this);

    }

}