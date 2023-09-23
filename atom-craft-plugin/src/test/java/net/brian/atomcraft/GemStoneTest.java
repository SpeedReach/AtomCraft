package net.brian.atomcraft;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.AtomItem;
import net.brian.atomcraft.api.ConfiguredItem;
import net.brian.atomcraft.api.exception.CfgItemNotFoundException;
import net.brian.atomcraft.itemmodifiers.gemstone.GemStoneModifier;
import net.brian.atomcraft.itemmodifiers.gemstone.GemstoneData;
import net.brian.atomcraft.items.AtomItemBuilder;
import net.brian.atomcraft.items.BukkitConfiguredItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
    void testApplyGemStone() throws CfgItemNotFoundException {

        ConfiguredItem configuredItem= new BukkitConfiguredItem(
                "test-item",
                Material.DIAMOND_SWORD,
                0,
               List.of("hi","there"),
                Map.of("ATTACK_DAMAGE",10.0),
                Map.of(),
                Map.of(),
                List.of()
        );
        ItemStack item = new AtomItemBuilder(configuredItem).build();

        //Every ItemStack possesses a distinct Unique ID,
        // serving as a means to pinpoint the item within the cache.
        // This ID only undergoes an update when the item is modified.
        // The LiveItemCache is responsible for housing deserialized ItemStacks for a brief duration.
        AtomItem atomItem = plugin.getLiveItemCache().getItem(item).get();

        AtomItemBuilder itemBuilder = new AtomItemBuilder(atomItem);
        itemBuilder.addModifier(GemStoneModifier.TYPE_INFO,new GemstoneData("",List.of(new GemstoneData.StatModifier(
                GemstoneData.StatModifierType.FLAT,
                10.0,
                "ATTACK_DAMAGE"
        ))));
        // The Unique ID is updated here.
        item = itemBuilder.build();
        atomItem = plugin.getLiveItemCache().getItem(item).get();
        assert atomItem.getFlatPlayerStat("ATTACK_DAMAGE") == 20.0;
        assert atomItem.getRelativePlayerStat("ATTACK_DAMAGE") == 10.0;

    }

}
