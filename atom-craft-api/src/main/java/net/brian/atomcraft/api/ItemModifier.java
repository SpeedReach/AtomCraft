package net.brian.atomcraft.api;

import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.data.ItemModifierData;

public interface ItemModifier<T extends ItemModifierData> {

    String getID();

    ItemBuilder.Cache apply(ItemBuilder.Cache builderCache,T modifierData);

    int getPriority();

    Class<T> getDataClass();

}
