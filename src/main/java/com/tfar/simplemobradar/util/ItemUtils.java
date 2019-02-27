package com.tfar.simplemobradar.util;

import com.tfar.simplemobradar.MainClass;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtils {

    public static ItemStack getHeldRadar(EntityPlayer player) {
        return player.getHeldItemMainhand();
    }

    public static String getMobName(ItemStack stack) {
        return I18n.format("entity." + Reference.mobs.get(stack.getTagCompound().getInteger("mobtype")).getName() + ".name");
    }

    public static String getPassiveName(ItemStack stack) {
        return I18n.format("entity." + Reference.animals.get(stack.getTagCompound().getInteger("mobtype")).getName() + ".name");
    }

    public static int getDistance(EntityPlayer player, ItemStack stack) {
        return (int) player.getDistance(stack.getTagCompound().getDouble("X position"), stack.getTagCompound().getDouble("Y position"), stack.getTagCompound().getDouble("Z position"));
    }

    public static int getTotal(ItemStack stack) {
        return stack.getTagCompound().getInteger("Size");
    }
}