package net.brian.atomcraft.api.services;

import net.brian.atomcraft.api.ConfiguredItem;

import java.util.Optional;

public interface ConfigItemRegistry {

    Optional<ConfiguredItem> getItem(String id);

    void register(ConfiguredItem item);

}
