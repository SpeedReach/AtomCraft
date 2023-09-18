package net.brian.atomcraft;

import lombok.Getter;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.ItemDataRegistry;
import net.brian.atomcraft.api.ItemModifierRegistry;
import net.brian.atomcraft.api.PlayerStatsImplBridge;
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

    @Override
    public void onEnable() {
        instance = this;
        modifierRegistry = new ItemModifierRegistryImpl(this);

    }

}