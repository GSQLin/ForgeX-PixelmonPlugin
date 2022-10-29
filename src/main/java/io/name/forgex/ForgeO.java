package io.name.forgex;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ForgeO implements Listener {
    @EventHandler
    public void onForgeOld(catserver.api.bukkit.event.ForgeEvent event){
        if ((Object)event.getForgeEvent() instanceof BeatWildPixelmonEvent){
            BeatWildPixelmonEvent e = (BeatWildPixelmonEvent) (Object)event.getForgeEvent();
            /*这个事件的玩家再两个不同版本下是不同类的所以获取方式如下
            当你翻看当你翻看BeatWildPixelmonEvent这类的时候可以发现1这类的时候可以发现1.12.2
            和1.16.5版本下的这个事件里面有一个变量player 1.12.2和
            entityplayermp的类型所以下面获取出来的object强转成entityplayermp就可以了
            1.16.5也一样转的是ServerPlayerEntity
            下方方法的中有一个"player"的string参数这是因为再BeatWildPixelmonEvent类中的变脸直接被
            命名为player
             */
            Object o = PixelUtil.getField(BeatWildPixelmonEvent.class,e,"player");
            Player p = ((net.minecraft.entity.player.EntityPlayerMP)o).getBukkitEntity();
            p.sendMessage("§a1.12.2BeatWildPixelmonEvent事件测试");
        }
    }
}
