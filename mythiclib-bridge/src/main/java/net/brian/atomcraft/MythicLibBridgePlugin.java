package net.brian.atomcraft;

import net.brian.atomcraft.api.AtomCraft;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicLibBridgePlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        AtomCraft atomCraft = (AtomCraft) Bukkit.getPluginManager().getPlugin(AtomCraft.PLUGIN_NAME);
        assert atomCraft != null;
        atomCraft.setStatsImplBridge(new MythicLibBridge());
    }

}
