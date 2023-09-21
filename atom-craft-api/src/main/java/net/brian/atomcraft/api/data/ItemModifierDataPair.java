package net.brian.atomcraft.api.data;

import net.brian.atomcraft.api.ItemModifier;

public record ItemModifierDataPair<T>(
        ItemModifier<T> modifier,
        T data
) {
}
