package net.brian.atomcraft;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.brian.atomcraft.api.AtomCraft;
import net.brian.atomcraft.api.ConfiguredItem;
import net.brian.atomcraft.items.AtomItemBuilder;
import net.brian.atomcraft.items.AtomItemStack;
import net.brian.atomcraft.items.BukkitConfiguredItem;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

public class TestW {
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


    }



}
