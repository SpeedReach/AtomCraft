package net.brian.atomcraft.api;

import net.brian.atomcraft.api.data.ItemJsonData;

public interface ItemModifier<T> {

    String getID();

    ItemJsonData apply(ItemJsonData builderCache, T modifierData);

    int getPriority();

    Class<T> getDataClass();

}
