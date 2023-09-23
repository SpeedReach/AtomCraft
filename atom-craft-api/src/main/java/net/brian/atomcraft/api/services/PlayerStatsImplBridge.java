package net.brian.atomcraft.api.services;


import net.brian.atomcraft.api.AtomItem;

/**
 * This interface is used to bridge the gap between AtomCraft and the player stats implementation.
 * Currently, the only player stat implementation is MythicLib, but we might consider writing our own in the future.
 */
public interface PlayerStatsImplBridge {

    void equip(AtomItem atomItem,AtomEquipmentSlot slot);

}