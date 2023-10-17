package net.brian.atomcraft.addons;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.addons.modifiers.gemstone.GemStoneModifier;
import net.brian.atomcraft.addons.modifiers.gemstone.GemstoneData;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ItemBuilder;
import net.brian.atomcraft.api.exception.CfgItemNotFoundException;
import net.brian.atomcraft.api.models.ConfiguredItem;
import net.brian.atomcraft.api.models.json.ItemModifierContainer;
import net.brian.atomcraft.items.AtomItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GemStoneTest {
    private static ServerMock server;
    private static AtomCraft plugin;
    private static AtomCraftAddons addons;

    @BeforeAll
    public static void load() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(AtomCraftPlugin.class);
        addons = MockBukkit.load(AtomCraftAddons.class);
    }

    @AfterAll
    public static void unload() {
        MockBukkit.unmock();
    }


    @Test
    void testApplyGemStone() throws CfgItemNotFoundException {
        ConfiguredItem configuredItem= new ConfiguredItem(
                "test-item",
                Material.DIAMOND_SWORD,
                0,
                ImmutableList.of("hi","there"),
                ImmutableMap.of("ATTACK_DAMAGE",10.0),
                ImmutableMap.of(),
                ImmutableMap.of()
        );
        plugin.getConfigItemRegistry().register(configuredItem);
        ItemStack item = new AtomItemBuilder(configuredItem).build();

        //Every ItemStack possesses a distinct Unique ID,
        // serving as a means to pinpoint the item within the cache.
        // This ID only undergoes an update when the item is modified.
        // The LiveItemCache is responsible for housing deserialized ItemStacks for a brief duration.
        AtomItem atomItem = plugin.getLiveItemCache().getItem(item).get();
        UUID originUUID = atomItem.getUniqueId();

        ItemBuilder itemBuilder = new AtomItemBuilder(atomItem);
        itemBuilder.addModifier(GemStoneModifier.INSTANCE,new GemstoneData("", "",List.of(new GemstoneData.StatModifier(
                GemstoneData.StatModifierType.FLAT,
                10.0,
                "ATTACK_DAMAGE"
        ))));
        // The Unique ID is updated here.
        item = itemBuilder.build();
        atomItem = plugin.getLiveItemCache().getItem(item).get();
        Assertions.assertEquals(20.0,atomItem.getFlatPlayerStat("ATTACK_DAMAGE"));

        itemBuilder = new AtomItemBuilder(atomItem);
        System.out.println(itemBuilder.getModifiers());
        itemBuilder.getModifiers().entrySet().removeIf(entry -> Objects.equals(entry.getValue().type(), GemStoneModifier.ID));

        item = itemBuilder.build();
        atomItem = plugin.getLiveItemCache().getItem(item).get();
        UUID newUUID = atomItem.getUniqueId();
        Assertions.assertNotEquals(originUUID,newUUID);
        Assertions.assertEquals(10.0,atomItem.getFlatPlayerStat("ATTACK_DAMAGE"));

    }



}
