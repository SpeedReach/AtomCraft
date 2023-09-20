package net.brian.atomcraft;

import be.seeseemelk.mockbukkit.MockBukkit;
import net.brian.atomcraft.api.AtomCraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class TestW {

    private static AtomCraft plugin;

    @BeforeAll
    public static void load(){
        MockBukkit.mock();
        plugin = MockBukkit.load(AtomCraftPlugin.class);
    }

    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    public static void unload(){
        MockBukkit.unmock();
    }

    void testApplyGemStone(){


    }



}
