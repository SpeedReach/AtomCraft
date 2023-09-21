package net.brian.atomcraft.itemdata;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Getter;

import java.lang.reflect.Type;

public record RawItemData(@Getter JsonElement rawJson) {

    public static class Serializer implements JsonSerializer<RawItemData> {
        @Override
        public JsonElement serialize(RawItemData src, Type typeOfSrc, JsonSerializationContext context) {
            return src.rawJson;
        }
    }

}
