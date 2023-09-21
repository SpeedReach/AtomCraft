package net.brian.atomcraft.itemmodifiers.gemstone;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.brian.atomcraft.api.data.ItemModifierData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GemstoneData implements ItemModifierData {

    private String gemStoneBase64;
    private List<StatModifier> modifiers;

    public GemstoneData(String gemStoneBase64,List<StatModifier> modifiers){
        this.gemStoneBase64 = Objects.requireNonNull(gemStoneBase64);
        this.modifiers = Objects.requireNonNullElseGet(modifiers, ArrayList::new);
    }


    //This constructor is only for deserialization use
    public GemstoneData(){

    }


    @Override
    public String getType() {
        return GemStoneModifier.ID;
    }


    public ImmutableList<StatModifier> getModifiers(){
        return ImmutableList.copyOf(modifiers);
    }

    public enum StatModifierType{
        FLAT,RELATIVE
    }

    public record StatModifier(
            StatModifierType type,
            double value,
            String target
    ){

    }

    public String getSerializedGemStone(){
        return gemStoneBase64;
    }

}
