package net.brian.atomcraft.api;


import net.brian.atomcraft.api.services.*;

public interface AtomCraft {

    ItemModifierRegistry getModifierRegistry();

    ItemDataRegistry getDataRegistry();

    PlayerStatsImplBridge getStatsImplBridge();

    ConfigItemRegistry getConfigItemRegistry();

    GsonProvider getGsonProvider();

    AtomItemStackBridge getItemStackBridge();

}
