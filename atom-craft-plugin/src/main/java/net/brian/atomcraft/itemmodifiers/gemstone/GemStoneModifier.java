package net.brian.atomcraft.itemmodifiers.gemstone;

import lombok.Getter;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.data.ItemJsonData;

public class GemStoneModifier implements ItemModifier<GemstoneData> {


    public static final String ID = "gem_stone";


    @Override
    public String getID() {
        return ID;
    }

    @Override
    public ItemJsonData apply(ItemJsonData cache, GemstoneData modifierData) {
        for(GemstoneData.StatModifier modifier : modifierData.getModifiers()){
            switch (modifier.type()){
                case FLAT -> cache.flatPlayerStats().compute(modifier.target(), (ignore, oldValue) -> {
                    if(oldValue == null){
                        return modifier.value();
                    }
                    else{
                        return oldValue + modifier.value();
                    }
                });
                case RELATIVE -> cache.relativePlayerStats().compute(modifier.target(), (ignore, oldValue) -> {
                    if(oldValue == null){
                        return modifier.value();
                    }
                    else{
                        return oldValue + modifier.value();
                    }
                });
            }
        }
        return cache;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public Class<GemstoneData> getDataClass() {
        return GemstoneData.class;
    }
}
