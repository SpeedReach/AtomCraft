package net.brian.atomcraft.api.services;

import java.util.Optional;

/**
 * Stores the class type of the data that is stored in the item.
 * Is often used in deserialization.
  */
public interface ItemDataRegistry {

    <T> void register(String id, Class<T> dataClass);

    Optional<Class<?>> getDataClass(String id);

}
