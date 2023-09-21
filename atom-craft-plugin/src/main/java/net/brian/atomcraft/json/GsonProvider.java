package net.brian.atomcraft.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class GsonProvider implements net.brian.atomcraft.api.services.GsonProvider {
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = null;

    public GsonProvider(){
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    }

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
