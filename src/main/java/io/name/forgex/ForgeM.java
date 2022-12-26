package io.name.forgex;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ForgeM implements Listener {
    @EventHandler
    public void onForgeM(catserver.api.bukkit.ForgeEventV2 event) throws Exception {
        GSQListener.play(event);
    }
}
