package me.gsqlin.pretendpoke;

import com.pixelmonmod.pixelmon.api.events.FishingEvent;

import static me.gsqlin.pretendpoke.PixelUtil.forgeEventMethod;
public class GSQListener implements Listener{
    @EventHandler
    public void onMyForgeEvent(MyForgeEvent event)throws Exception{
        if (event.getForgeEvent() instanceof PokeLootEvent.GetDrops) {
            PokeLootEvent.GetDrops e = (PokeLootEvent.GetDrops) event.getForgeEvent();
            //监听打开精灵奖励箱事件
        }
    }
}
