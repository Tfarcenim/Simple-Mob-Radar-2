package com.tfar.simplemobradar.items;

import com.tfar.simplemobradar.MainClass;
import com.tfar.simplemobradar.config.ConfigHandler;
import com.tfar.simplemobradar.init.ModItems;
import com.tfar.simplemobradar.util.IHasModel;
import com.tfar.simplemobradar.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.tfar.simplemobradar.config.ConfigHandler.*;
import static java.lang.Math.round;

public class ItemSimpleMobRadar extends Item implements IHasModel {
    public ItemSimpleMobRadar(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        this.setCreativeTab(CreativeTabs.MISC);
        ModItems.ITEMS.add(this);
    }

    private static int r = ConfigHandler.RANGE_ANIMALS;
    private List<Entity> entityList;
    private int saved_mob;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        if (player.getHeldItem(hand).getTagCompound() == null) {
            writeToNBT(player.getHeldItem(hand));
        }
        BlockPos pos = player.getPosition();
        if (player.isSneaking()) {
            if (!world.isRemote) {
                changeTargetMob(player.getHeldItem(hand));
                player.getCooldownTracker().setCooldown(this, 7);
                return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
            }
        } else if (!world.isRemote) {
            player.getCooldownTracker().setCooldown(this, 20);

            entityList = getMobList(world, pos, player, hand);
            if (entityList != null && entityList.size() > 0) {
                player.getHeldItem(hand).getTagCompound().setInteger("State", 1);
                saved_mob = getClosestMobs(pos.getX(), pos.getY(), pos.getZ(), player, hand);
                ((EntityLivingBase) entityList.get(saved_mob)).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 1));
            } else {
                player.getHeldItem(hand).getTagCompound().setInteger("State", 0);
            }

            return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
        return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 720000;
    }

    public List<Entity> getMobList(World Worldin, BlockPos pos, EntityPlayer player, EnumHand hand) {

        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        List<Entity> entities = Worldin.getEntitiesWithinAABB(Reference.mob_class.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type")), new AxisAlignedBB(i - r, j - r, k - r, i + r, j + r, k + r));
        if (entities.size() > 0) {
            if (DISPLAY_TOTAL_MOBS) {
                player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Found: " + entities.size() + " " + Reference.mobs.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type"))));
            }
        }
        return entities;
    }

    public void changeTargetMob(ItemStack stack) {
        stack.getTagCompound().setInteger("State", 2);
        int temp = stack.getTagCompound().getInteger("mob type");
        if ((temp + 1) >= Reference.valid_mobs.size()) {
            stack.getTagCompound().setInteger("mob type", 0);
        } else {
            stack.getTagCompound().setInteger("mob type", temp + 1);
        }
    }

    public int getClosestMobs(double x, double y, double z, EntityPlayer player, EnumHand hand) {
        List<Entity> entities = entityList;
        int closest_mob = 0;
        double distance;
        double closest = 2000000000;
        for (int i = 0; i < entities.size(); i++) {
            distance = player.getDistance(entities.get(i).posX,entities.get(i).posY,entities.get(i).posZ);
            if (distance < closest) {
                closest = distance;
                closest_mob = i;
            }
        }

        player.getHeldItem(hand).getTagCompound().setDouble("X position", entities.get(closest_mob).posX);
        player.getHeldItem(hand).getTagCompound().setDouble("Y position", entities.get(closest_mob).posY);
        player.getHeldItem(hand).getTagCompound().setDouble("Z position", entities.get(closest_mob).posZ);

        if (DISPLAY_ELEVATION)
            player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Elevation Difference: " + round(entities.get(closest_mob).posY - y)));



        return closest_mob;
    }

    @Override
    public void registerModels() {
        MainClass.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public void writeToNBT(ItemStack stack) {
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("mob type", 0);
        stack.getTagCompound().setInteger("State", 2);

    }

    public int getState(ItemStack stack) {
        if (/*ItemUtils.verifyNBT(stack)*/ true) {
            return stack.getTagCompound().getInteger("State");
        }

        return 0;
    }
}