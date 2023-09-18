package net.brian.atomcraft;

import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.services.ItemModifierRegistry;
import net.brian.atomcraft.api.data.ItemModifierData;

import java.util.HashMap;
import java.util.Optional;

public class ItemModifierRegistryImpl implements ItemModifierRegistry {

    private final AtomCraft plugin;

    private final HashMap<String ,ItemModifier<?>> modifiers = new HashMap<>();

    ItemModifierRegistryImpl(AtomCraft plugin){
        this.plugin = plugin;
    }

    @Override
    public <T extends ItemModifier<D>, D extends ItemModifierData> void register(String ID, T modifier) {
        modifiers.put(ID,modifier);

    }

    @Override
    public <T extends ItemModifier<D>, D extends ItemModifierData> Optional<T> getModifier(String ID) {
        return Optional.ofNullable((T) modifiers.get(ID));
    }

}
