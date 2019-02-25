package com.tfar.simplemobradar.util;


import com.tfar.simplemobradar.items.ItemSimpleMobRadar;
import com.tfar.simplemobradar.items.ItemSimplePassiveRadar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class ItemUtils {

    public static boolean verifyNBT(ItemStack stack) {
        if (stack.isEmpty() || !(!(stack.getItem() instanceof ItemSimpleMobRadar) && !(stack.getItem() instanceof ItemSimplePassiveRadar))) {
            return false;
        } else if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        return true;
    }

    public static ItemStack getHeldRadar(EntityPlayer player) {
        return player.getHeldItemMainhand();
    }
    public static String getMobName(ItemStack stack){
        return Reference.mobs.get(stack.getTagCompound().getInteger("mob type"));
    }
    public static int getDistance(EntityPlayer player, ItemStack stack){
        return (int)player.getDistance( stack.getTagCompound().getDouble("X position"), stack.getTagCompound().getDouble("Y position"),  stack.getTagCompound().getDouble("Z position"));
    }
    public static int getTotal(ItemStack stack){
        return  stack.getTagCompound().getInteger("Size");
    }
}