package io.name.forgex;

import com.pixelmonmod.pixelmon.Pixelmon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PixelUtil {
    public static String pixelVersion = Pixelmon.getVersion();
    public static String bukkitVersion = Bukkit.getBukkitVersion().contains("1.12.2")? "1.12.2" : "1.16.5";

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
    /*
    获取一个对象里边的变量,如果变量类似arraylist这类的你修改了,对象内的也一样,他们是同一个变量
    */
    public static Object getField(Class<?> c,Object o,String name) throws Exception {
        Object ot = null;
        while (ot == null){
            for (Field f:c.getDeclaredFields()){
                if (f.getName().equals(name)){
                    f.setAccessible(true);
                    ot = f.get(o);
                    break;
                }
            }
            c = c.getSuperclass();
            if (c == null) break;
        }
        return ot;
    }
    //获取一个对象中的方法(自动往父级上找到为止(最后没有则返回null))
    public static Method getMethod(Class<?> c,String name,Class<?>... a) throws Exception {
        Method method = null;
        do {
            for (Method met : c.getDeclaredMethods()) {
                if (met.getName().equals(name)) {
                    met.setAccessible(true);
                    method = c.getDeclaredMethod(name, a);
                    break;
                }
            }
            c = c.getSuperclass();
        } while (c != null);
        return method;
    }
    //对一个对象中修改里边的变量
    public static void setVariable(Class<?> c,Object o,String name,Object value) throws Exception {
        do {
            for (Field f:c.getDeclaredFields()){
                if (f.getName().equals(name)){
                    f.setAccessible(true);
                    f.set(o,value);
                    break;
                }
            }
            c = c.getSuperclass();
        }while (c != null);
    }
    //模仿CraftItemStack类中的方法asBukkitCopy
    public static ItemStack asBukkitCopy(net.minecraft.item.ItemStack itemStack) throws Exception {
        Class<?> craftItemStack;
        if (PixelUtil.pixelVersion.equalsIgnoreCase("8.4.2")){
            craftItemStack = Class.forName("org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack");
        }else{
            craftItemStack = Class.forName("org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack");
        }

        Method met = PixelUtil.getMethod(craftItemStack,"asBukkitCopy",net.minecraft.item.ItemStack.class);
        return (ItemStack) met.invoke(craftItemStack, itemStack);
    }
}
