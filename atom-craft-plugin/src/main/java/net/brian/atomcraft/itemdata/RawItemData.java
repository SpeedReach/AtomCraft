package net.brian.atomcraft.itemdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Getter;

import java.lang.reflect.Type;

public class RawItemData {

    @Getter private final JsonObject rawJson;

    public RawItemData(JsonObject rawJson){
            this.rawJson = rawJson;
    }

    public static class Serializer implements JsonSerializer<RawItemData> {

        @Override
        public JsonElement serialize(RawItemData src, Type typeOfSrc, JsonSerializationContext context) {
            return src.rawJson;
        }
    }

}
