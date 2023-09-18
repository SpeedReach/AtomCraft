package net.brian.atomcraft.api;


import net.brian.atomcraft.api.services.ConfigItemRegistry;
import net.brian.atomcraft.api.services.ItemDataRegistry;
import net.brian.atomcraft.api.services.ItemModifierRegistry;
import net.brian.atomcraft.api.services.PlayerStatsImplBridge;

public interface AtomCraft {

    ItemModifierRegistry getModifierRegistry();

    ItemDataRegistry getDataRegistry();

    PlayerStatsImplBridge getStatsImplBridge();

    ConfigItemRegistry getConfigItemRegistry();

}
