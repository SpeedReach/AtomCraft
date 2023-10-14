package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.models.ConfiguredItem;

import java.util.Optional;

public interface ConfigItemRegistry {

    Optional<ConfiguredItem> getItem(String id);

    void register(ConfiguredItem item);

}
