package me.gsqlin.pretendpoke;

import com.pixelmonmod.pixelmon.api.events.FishingEvent;

import static me.gsqlin.pretendpoke.PixelUtil.forgeEventMethod;
public class GSQListener {

    public static void play(Object event) throws Exception {
        if (forgeEventMethod.invoke(event) instanceof FishingEvent.Reel);
    }
}