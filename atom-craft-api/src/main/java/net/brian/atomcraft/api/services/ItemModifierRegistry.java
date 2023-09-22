package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.ItemModifier;

import java.util.Optional;

/**
 *Stores all the item modifiers, is often read when deserializing an item(Provide data class info), and when applying modifiers to [ItemBuilder]
*/
public interface ItemModifierRegistry {

    <T extends ItemModifier<D>,D> void register(T modifier);

    <T extends ItemModifier<D>,D> Optional<T> getModifier(String ID);

}
