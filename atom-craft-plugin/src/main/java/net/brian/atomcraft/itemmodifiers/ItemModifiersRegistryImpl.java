package net.brian.atomcraft.itemmodifiers;

import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.services.ItemModifierRegistry;

import java.util.Optional;

public class ItemModifiersRegistryImpl implements ItemModifierRegistry {

    @Override
    public <T extends ItemModifier<D>, D> void register(String ID, T modifier) {

    }

    @Override
    public <T extends ItemModifier<D>, D> Optional<T> getModifier(String ID) {
        return Optional.empty();
    }

}



