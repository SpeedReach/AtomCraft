package net.brian.atomcraft.api;


public interface AtomCraft {

    ItemModifierRegistry getModifierRegistry();

    ItemDataRegistry getDataRegistry();

    PlayerStatsImplBridge getStatsImplBridge();

}
