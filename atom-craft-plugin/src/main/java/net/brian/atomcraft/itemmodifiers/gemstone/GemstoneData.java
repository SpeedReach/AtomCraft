package net.brian.atomcraft.itemmodifiers.gemstone;


import java.util.List;

public record GemstoneData(
        String displayName,
        String gemStoneBase64,
        List<StatModifier> modifiers
) {

    public enum StatModifierType{
        FLAT,RELATIVE
    }

    public record StatModifier(
            StatModifierType type,
            double value,
            String target
    ){

    }

}
