package net.brian.atomcraft.api;

import net.brian.atomcraft.api.data.ItemJsonData;

public interface ItemModifier<T> {

    TypeInfo<T> getTypeInfo();

    ItemJsonData apply(ItemJsonData builderCache, T modifierData);

    int getPriority();

    public record TypeInfo<T>(
            String id,
            Class<T> dataClass
    ){

    }
}
