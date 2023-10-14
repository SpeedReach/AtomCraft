package net.brian.atomcraft;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.models.ConfiguredItem;
import net.brian.atomcraft.api.models.json.ItemJsonData;
import net.brian.atomcraft.itemmodifiers.gemstone.GemStoneModifier;
import net.brian.atomcraft.itemmodifiers.gemstone.GemstoneData;
import net.brian.atomcraft.items.AtomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class GemStoneTest {
    private static ServerMock server;
    private static AtomCraft plugin;

    @BeforeAll
    public static void load() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(AtomCraftPlugin.class);
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }


    @Test
    void testApplyGemStone(){
        String id = "test-item";
        ConfiguredItem configuredItem =
                new ConfiguredItem(
                        "test-item",
                        Material.DIAMOND_SWORD,
                        0,
                        ImmutableList.of("hi", "there"),
                        ImmutableMap.of("ATTACK_DAMAGE", 10.0),
                        ImmutableMap.of(),
                        ImmutableMap.of());
        AtomItemBuilder itemBuilder = new AtomItemBuilder(configuredItem);
        itemBuilder.addModifier(GemStoneModifier.TYPE_INFO,new GemstoneData("CoolStone","",ImmutableList.of(new GemstoneData.StatModifier(
                GemstoneData.StatModifierType.FLAT,
                10.0,
                "ATTACK_DAMAGE"
        ))));
        ItemStack item = itemBuilder.build();
        Optional<ItemJsonData> json = plugin.getItemStackBridge().readJson(item.getItemMeta());
        System.out.println(json);
    }



}
