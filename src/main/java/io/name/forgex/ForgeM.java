package io.name.forgex;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ForgeM implements Listener {
    @EventHandler
    public void onForgeM(catserver.api.bukkit.ForgeEventV2 event) throws Exception {
        if (ForgeX.getForgeEvent.invoke(event) instanceof BeatWildPixelmonEvent){
            BeatWildPixelmonEvent e = (BeatWildPixelmonEvent) ForgeX.getForgeEvent.invoke(event) ;
            //对应上方1.12.2写成1.16.5(由于1.16.5被判定比1.12.2版本高,所以idea再判断两个同类会只依赖高版本的)
            //所以这边不用转成Object
            Object o = PixelUtil.getField(BeatWildPixelmonEvent.class,e,"player");
            Player p = ((net.minecraft.entity.player.ServerPlayerEntity)o).getBukkitEntity();
            p.sendMessage("§61.16.5BeatWildPixelmonEvent事件测试");
        }
    }
}
