package com.tfar.simplemobradar.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemUtils {

    public static ItemStack getHeldRadar(EntityPlayer player) {
        return player.getHeldItemMainhand();
    }

    public static String getMobName(ItemStack stack){
        return I18n.format("entity." + Reference.sorted_mobs.get(stack.getTagCompound().getInteger("mobtype"))+ ".name");
    }

    public static String getPassiveName(ItemStack stack){
        return I18n.format("entity." + Reference.sorted_animals.get(stack.getTagCompound().getInteger("mobtype"))+ ".name");
    }

    public static int getDistance(EntityPlayer player, ItemStack stack){
        return (int)player.getDistance( stack.getTagCompound().getDouble("X position"), stack.getTagCompound().getDouble("Y position"),  stack.getTagCompound().getDouble("Z position"));
    }

    public static int getTotal(ItemStack stack){
        return  stack.getTagCompound().getInteger("Size");
    }












}