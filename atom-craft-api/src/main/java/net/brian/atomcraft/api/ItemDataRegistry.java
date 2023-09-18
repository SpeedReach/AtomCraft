package net.brian.atomcraft.api;

import java.util.Optional;

public interface ItemDataRegistry {

    <T> void register(String id, Class<T> dataClass);

    <T> Optional<Class<T>> getDataClass(String id);

}
