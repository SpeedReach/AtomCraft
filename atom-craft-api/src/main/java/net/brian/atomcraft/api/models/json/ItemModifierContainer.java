package net.brian.atomcraft.api.models.json;

public record ItemModifierContainer(
        String type,
        Object data
) {

    public static final String TYPE_KEY = "type";
    public static final String DATA_KEY = "data";
}
