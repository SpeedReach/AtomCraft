package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.ItemModifier;

import java.util.Optional;


public interface ItemModifierRegistry {

    <T extends ItemModifier<D>,D> void register(String ID, T modifier);

    <T extends ItemModifier<D>,D> Optional<T> getModifier(String ID);

}
