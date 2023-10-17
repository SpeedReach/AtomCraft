package net.brian.atomcraft.addons;

import net.brian.atomcraft.AtomCraftPlugin;
import net.brian.atomcraft.addons.modifiers.gemstone.GemStoneModifier;
import net.brian.atomcraft.api.AtomCraft;
import org.bukkit.plugin.java.JavaPlugin;

public class AtomCraftAddons extends JavaPlugin {

    @Override
    public void onEnable() {
        AtomCraftPlugin.instance.getModifierRegistry().register(GemStoneModifier.INSTANCE);
    }

}
