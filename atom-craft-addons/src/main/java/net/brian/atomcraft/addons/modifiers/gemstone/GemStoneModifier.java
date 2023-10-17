package net.brian.atomcraft.addons.modifiers.gemstone;

import net.brian.atomcraft.api.ItemModifier;
import net.brian.atomcraft.api.models.json.ItemJsonData;

public final class GemStoneModifier implements ItemModifier<GemstoneData> {

    public static final String ID = "gem_stone";
    public static final TypeInfo<GemstoneData> TYPE_INFO = new TypeInfo<>(ID,GemstoneData.class);
    public static final GemStoneModifier INSTANCE = new GemStoneModifier();

    private GemStoneModifier(){}


    @Override
    public TypeInfo<GemstoneData> getTypeInfo() {
        return TYPE_INFO;
    }

    @Override
    public void apply(ItemJsonData cache, GemstoneData modifierData) {
        for(GemstoneData.StatModifier modifier : modifierData.modifiers()){
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
    }

    @Override
    public int getPriority() {
        return 2;
    }

}
