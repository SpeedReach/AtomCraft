package net.brian.atomcraft.api.models;

import net.brian.atomcraft.api.ItemModifier;

public record ItemModifierDataPair<T>(
        ItemModifier<T> modifier,
        T data
) {
}
