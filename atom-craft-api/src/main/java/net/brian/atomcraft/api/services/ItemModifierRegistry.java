package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.data.ItemModifierData;

import java.util.Optional;


public interface ItemModifierRegistry {

    <T extends ItemModifier<D>,D extends ItemModifierData> void register(String ID, T modifier);

    <T extends ItemModifier<D>,D extends ItemModifierData> Optional<T> getModifier(String ID);

}
