package net.brian.atomcraft.itemmodifiers.gemstone;

import lombok.Getter;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.itemmodifiers.gemstone.GemstoneData;

public class GemStoneModifier implements ItemModifier<GemstoneData> {

    @Getter
    public static final String ID = "gem_stone";


    @Override
    public ItemBuilder apply(ItemBuilder builder, GemstoneData modifierData) {
        for(GemstoneData.StatModifier modifier : modifierData.getModifiers()){
            switch (modifier.type()){
                case FLAT -> builder.computeFlatPlayerStat(modifier.target(), (ignore, oldValue) -> {
                    if(oldValue == null){
                        return modifier.value();
                    }
                    else{
                        return oldValue + modifier.value();
                    }
                });
                case RELATIVE -> builder.computeRelativePlayerStat(modifier.target(), (ignore, oldValue) -> {
                    if(oldValue == null){
                        return modifier.value();
                    }
                    else{
                        return oldValue + modifier.value();
                    }
                });
            }
        }
        return builder;
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
