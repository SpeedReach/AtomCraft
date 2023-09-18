package net.brian.atomcraft.api.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public record ItemJsonData(
        HashMap<String, Double> flatPlayerStats,
        HashMap<String, Double> relativePlayerStats,
        HashMap<String, ItemModifierData> modifiers,
        HashMap<String, Object> data
) {



}
