package net.brian.atomcraft.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class GsonProvider implements net.brian.atomcraft.api.services.GsonProvider {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = null;

    @Override
    public Gson getGson() {
        if(gson != null){
            return gson;
        }
        gson = builder.create();
        return gson;
    }

    @Override
    public void registerTypeAdapter(Type type, Object adapter) {
        builder.registerTypeAdapter(type, adapter);
    }
}
