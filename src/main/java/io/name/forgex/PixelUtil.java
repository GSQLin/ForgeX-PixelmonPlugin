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
    public static Method forgeEventMethod;

    //forgeevent的获取
    static String cls = PixelUtil.bukkitVersion.equalsIgnoreCase("1.12.2") ?
            "catserver.api.bukkit.event.ForgeEvent" : "catserver.api.bukkit.ForgeEventV2";
    public static Method getForgeEventMethod(){
        try {
            return PixelUtil.getMethod(Class.forName(cls), "getForgeEvent");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getPlayerPartyStorage(Object p){
        if (PixelUtil.bukkitVersion.equalsIgnoreCase("1.12.2")){
            return getPlayerPartyStorage((net.minecraft.entity.player.EntityPlayerMP)p);
        }else{
            return getPlayerPartyStorage((net.minecraft.entity.player.ServerPlayerEntity)p);
        }
    }
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
    public static Object getPlayer(Player p) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> c = PixelUtil.bukkitVersion.equalsIgnoreCase("1.12.2")?Class.forName("org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity"): Class.forName("org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity");
        Method method = getMethod(c,"getHandle");
        return method.invoke(c.cast(p));
    }
    /*
    获取一个对象里边的变量,如果变量类似arraylist这类的你修改了,对象内的也一样,他们是同一个变量
    */
    public static Object getField(Class<?> c,Object o,String name) throws Exception {
        Object ot = null;
        Field f = null;
        while (f == null){
            try {
                f = c.getDeclaredField(name);
            }catch (Exception e){
                if (c.getSuperclass() == null) break;
                c = c.getSuperclass();
            }
        }
        if (f != null){
            f.setAccessible(true);
            ot = f.get(o);
        }
        return ot;
    }
    //获取一个对象中的方法(自动往父级上找到为止(最后没有则返回null))
    public static Method getMethod(Class<?> c,String name,Class<?>... a) {
        Method method = null;
        while (method == null){
            try {
                method = c.getDeclaredMethod(name, a);
            }catch (Exception e){
                if (c.getSuperclass() == null) break;
                c = c.getSuperclass();
            }
        }
        if (method != null) method.setAccessible(true);
        return method;
    }
    //对一个对象中修改里边的变量
    public static void setVariable(Class<?> c,Object o,String name,Object value) throws Exception {
        Field f = null;
        while (f == null){
            try {
                f = c.getDeclaredField(name);
            }catch (Exception e){
                if (c.getSuperclass() == null) break;
                c = c.getSuperclass();
            }
        }
        if (f != null) {
            f.setAccessible(true);
            f.set(o,value);
        }
    }
    //模仿CraftItemStack类中的方法asBukkitCopy
    public static ItemStack asBukkitCopy(net.minecraft.item.ItemStack itemStack) throws Exception {
        Class<?> craftItemStack;
        if (PixelUtil.bukkitVersion.equalsIgnoreCase("1.12.2")){
            craftItemStack = Class.forName("org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack");
        }else{
            craftItemStack = Class.forName("org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack");
        }

        Method met = PixelUtil.getMethod(craftItemStack,"asBukkitCopy",net.minecraft.item.ItemStack.class);
        return (ItemStack) met.invoke(craftItemStack, itemStack);
    }
    public static World getWorld(org.bukkit.World world) throws Exception {
        Class<?> c = bukkitVersion.equalsIgnoreCase("1.12.2")?Class.forName("org.bukkit.craftbukkit.v1_12_R1.CraftWorld"):Class.forName("org.bukkit.craftbukkit.v1_16_R3.CraftWorld");
        Method method = getMethod(c,"getHandle");
        return (World) method.invoke(c.cast(world));
    }
