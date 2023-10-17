package net.brian.atomcraft.api;


import net.brian.atomcraft.api.services.*;

public interface AtomCraft {

    String PLUGIN_NAME = "AtomCraft";

    ItemModifierRegistry getModifierRegistry();

    ItemDataRegistry getDataRegistry();

    PlayerStatsImplBridge getStatsImplBridge();
    void setStatsImplBridge(PlayerStatsImplBridge bridge);

    ConfigItemRegistry getConfigItemRegistry();

    GsonProvider getGsonProvider();

    AtomItemStackBridge getItemStackBridge();

    LiveItemCache getLiveItemCache();

}
