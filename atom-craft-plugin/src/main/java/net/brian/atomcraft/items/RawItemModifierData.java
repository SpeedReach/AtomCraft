package net.brian.atomcraft.items;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.brian.atomcraft.api.data.ItemModifierData;
import net.brian.atomcraft.itemdata.RawItemData;

public class RawItemModifierData extends RawItemData implements ItemModifierData{

    @Getter
    private final String type;

    @Getter
    private final String id;


    RawItemModifierData(String id,String type, JsonObject rawJson) {
        super(rawJson);
        this.id = id;
        this.type = type;
    }

}
