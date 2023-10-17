package net.brian.atomcraft.itemmodifiers;

import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.services.ItemModifierRegistry;

import java.util.HashMap;
import java.util.Optional;

public class ItemModifierRegistryImpl implements ItemModifierRegistry {

    private final AtomCraft plugin;

    private final HashMap<String ,ItemModifier<?>> modifiers = new HashMap<>();

    public ItemModifierRegistryImpl(AtomCraft plugin){
        this.plugin = plugin;
    }

    @Override
    public <T extends ItemModifier<D>, D> void register(T modifier) {
        modifiers.put(modifier.getTypeInfo().id(),modifier);

    }

    @Override
    public <T extends ItemModifier<D>, D> Optional<T> getModifier(String ID) {
        return Optional.ofNullable((T) modifiers.get(ID));
    }

}
