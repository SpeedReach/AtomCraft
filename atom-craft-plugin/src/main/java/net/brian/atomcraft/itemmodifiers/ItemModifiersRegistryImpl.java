package net.brian.atomcraft.itemmodifiers;

import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.data.ItemModifierData;
import net.brian.atomcraft.api.services.ItemModifierRegistry;

import java.util.Optional;

public class ItemModifiersRegistryImpl implements ItemModifierRegistry {

    @Override
    public <T extends ItemModifier<D>, D extends ItemModifierData> void register(String ID, T modifier) {

    }

    @Override
    public <T extends ItemModifier<D>, D extends ItemModifierData> Optional<T> getModifier(String ID) {
        return Optional.empty();
    }

}



