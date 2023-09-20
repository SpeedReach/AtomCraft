package net.brian.atomcraft.api.services;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public interface GsonProvider {

    Gson getGson();

    void registerTypeAdapter(Type type, Object adapter);

}
