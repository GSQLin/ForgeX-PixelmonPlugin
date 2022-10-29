package io.name.forgex;

import org.bukkit.plugin.java.JavaPlugin;

public class ForgeX extends JavaPlugin {
    @Override
    public void onEnable() {
        if (PixelUtil.pixelVersion.equals("8.4.2")){
            getServer().getPluginManager().registerEvents(new ForgeO(),this);
        }else{
            getServer().getPluginManager().registerEvents(new ForgeM(),this);
        }
        getLogger().info("§aPlugin loaded");
    }

    @Override
    public void onDisable() {
        getLogger().info("§3Plugin disabled");
    }
}
