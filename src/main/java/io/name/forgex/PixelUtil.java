package io.name.forgex;

import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PixelUtil {
    public static String pixelVersion = Pixelmon.getVersion();

    //不要用导入,要用静态
    public static com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage getPlayerPartyStorage(net.minecraft.entity.player.ServerPlayerEntity p){
        return com.pixelmonmod.pixelmon.api.storage.StorageProxy.getParty(p);
    }
    public static com.pixelmonmod.pixelmon.storage.PlayerPartyStorage getPlayerPartyStorage(net.minecraft.entity.player.EntityPlayerMP p){
        Class<?> pixelClass = Pixelmon.class;
        com.pixelmonmod.pixelmon.api.storage.IStorageManager storageM = null;
        try {
            storageM = (com.pixelmonmod.pixelmon.api.storage.IStorageManager) pixelClass.getField("storageManager").get(pixelClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert storageM != null;
        return storageM.getParty(p);
    }
    public static Player getPlayer(net.minecraft.entity.player.ServerPlayerEntity serverPlayerEntity){
        return serverPlayerEntity.getBukkitEntity().getPlayer();
    }
    public static Player getPlayer(net.minecraft.entity.player.EntityPlayerMP entityPlayerMP){
        return entityPlayerMP.getBukkitEntity().getPlayer();
    }
    public static Object getField(Class<?> c,Object o,String name){
        /*用于获取1.16.5和1.12.2同名事件获取不同的类型,然后再把返回来的object转成对应的类*/
        Object ot = null;
        try {
            Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            ot = f.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ot;
    }
}
