package net.brian.atomcraft.itemdata;

import net.brian.atomcraft.api.services.ItemDataRegistry;

import java.util.HashMap;
import java.util.Optional;

public class ItemDataRegistryImpl implements ItemDataRegistry {

    private final HashMap<String,Class<?>> dataClasses = new HashMap<>();

    public ItemDataRegistryImpl(){
    }

    @Override
    public <T> void register(String id, Class<T> dataClass) {
        dataClasses.put(id,dataClass);

    }

    @Override
    public Optional<Class<?>> getDataClass(String id) {
        return Optional.ofNullable(dataClasses.get(id));
    }

}
