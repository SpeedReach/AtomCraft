package net.brian.atomcraft.itemmodifiers.gemstone;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GemstoneData {

    private String gemStoneBase64;
    private List<StatModifier> modifiers;

    public GemstoneData(String gemStoneBase64,List<StatModifier> modifiers){
        this.gemStoneBase64 = Objects.requireNonNull(gemStoneBase64);
        this.modifiers = Objects.requireNonNullElseGet(modifiers, ArrayList::new);
    }


    //This constructor is only for deserialization use
    public GemstoneData(){

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
