package net.brian.atomcraft.api.services;

import java.util.Optional;


public interface ItemDataRegistry {

    <T> void register(String id, Class<T> dataClass);

    <T> Optional<Class<T>> getDataClass(String id);

}
