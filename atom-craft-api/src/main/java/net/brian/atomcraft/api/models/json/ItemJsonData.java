package net.brian.atomcraft.api.models.json;

import java.util.HashMap;
import java.util.UUID;


public record ItemJsonData(
        String configId,
        HashMap<String, Double> flatPlayerStats,
        HashMap<String, Double> relativePlayerStats,
        HashMap<String, ItemModifierContainer> modifiers,
        HashMap<String, Object> data
) {

    public static final String CONFIG_ID_KEY = "config_id";
    public static final String FLAT_PLAYER_STATS_KEY = "flat_player_stats";
    public static final String RELATIVE_PLAYER_STATS_KEY = "relative_player_stats";
    public static final String MODIFIERS_KEY = "modifiers";
    public static final String DATA_KEY = "data";

    public static ItemJsonData empty(String configId) {
        return new ItemJsonData(configId, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

}
