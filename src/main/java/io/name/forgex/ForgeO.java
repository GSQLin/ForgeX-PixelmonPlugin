package io.name.forgex;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ForgeO implements Listener {
    @EventHandler
    public void onForgeOld(catserver.api.bukkit.event.ForgeEvent event) throws Exception {
        GSQListener.play(event);
    }
}
