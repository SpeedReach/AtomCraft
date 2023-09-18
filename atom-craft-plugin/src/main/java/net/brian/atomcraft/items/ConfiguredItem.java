package net.brian.atomcraft.items;

import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

public record ConfiguredItem(
        @Getter Material material,
        @Getter int modelData,
        @Getter List<String> rawLore,
        @Getter ItemJsonData jsonData
) {


}
