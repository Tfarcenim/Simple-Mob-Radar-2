package com.tfar.simplemobradar.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemUtils {

    public static ItemStack getHeldRadar(EntityPlayer player) {
        return player.getHeldItemMainhand();
    }

    public static String getMobName(ItemStack stack){
        return Reference.mobs.get(stack.getTagCompound().getInteger("mob type"));
    }

    public static String getPassiveName(ItemStack stack){
        return Reference.animals.get(stack.getTagCompound().getInteger("mob type"));
    }

    public static int getDistance(EntityPlayer player, ItemStack stack){
        return (int)player.getDistance( stack.getTagCompound().getDouble("X position"), stack.getTagCompound().getDouble("Y position"),  stack.getTagCompound().getDouble("Z position"));
    }

    public static int getTotal(ItemStack stack){
        return  stack.getTagCompound().getInteger("Size");
    }
}