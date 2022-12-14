package io.name.forgex;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

public class ForgeX extends JavaPlugin {
    @Override
    public void onEnable() {
        PixelUtil.forgeEventMethod = PixelUtil.getForgeEventMethod();
        if (PixelUtil.bukkitVersion.equalsIgnoreCase("1.12.2")){
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
