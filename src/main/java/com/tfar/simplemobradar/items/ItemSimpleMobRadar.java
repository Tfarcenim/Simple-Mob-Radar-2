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

import static java.lang.Math.round;

public class ItemSimpleMobRadar extends Item implements IHasModel{
    public ItemSimpleMobRadar(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        this.setCreativeTab(CreativeTabs.MISC);
        ModItems.ITEMS.add(this);    }

    public static int r = ConfigHandler.range;
    public List<Entity> entityList;
    public int saved_mob;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        if (player.getHeldItem(hand).getTagCompound() == null){writeToNBT(player.getHeldItem(hand).getTagCompound(),player.getHeldItem(hand));}
        BlockPos pos = player.getPosition();
        if (player.isSneaking()) {
            if (!world.isRemote) {
            changeMobTarget(player.getHeldItem(hand));
                player.getCooldownTracker().setCooldown(this, 5);
                player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + " " + Reference.mobs.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type"))), true);
                return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));}
        } else if (!world.isRemote){player.getCooldownTracker().setCooldown(this, 20);

            entityList = getMobList(world, pos, player, hand);
            if (entityList != null && entityList.size() > 0) {
                saved_mob = getClosestMobToPlayer(pos.getX(), pos.getY(), pos.getZ(), player);
                if (entityList.get(saved_mob) instanceof EntityLivingBase){
                    ((EntityLivingBase)entityList.get(saved_mob)).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 1));}
            }
            return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));}
        return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {return 720000;}

    public List<Entity> getMobList(World Worldin, BlockPos pos, EntityPlayer player, EnumHand hand) {

        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        List<Entity> entities = Worldin.getEntitiesWithinAABB(Reference.mob_class.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type")), new AxisAlignedBB(i - r, j - r, k - r, i + r, j + r, k + r));
        if (entities.size() > 0) {
            player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Found: " + entities.size()+" "+Reference.mobs.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type"))));
        } else {
            player.sendMessage(new TextComponentString(TextFormatting.RED + "No " + Reference.mobs.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type")) + " nearby"));
        }
        return entities;
    }
    public void changeMobTarget(ItemStack stack) {
        int temp = stack.getTagCompound().getInteger("mob type");
        if ((temp+1)>=Reference.valid.size()){stack.getTagCompound().setInteger("mob type", 0);}
            else{stack.getTagCompound().setInteger("mob type", temp+1);}
    }
    public int getClosestMobToPlayer(double x, double y, double z, EntityPlayer player) {
        List<Entity> entities = entityList;
        int closest_mob = 0;
        double angle;
        int direction;
        String compass = "NORTH";
        double distance;
        double closest = 2000000000;
        for (int i = 0; i < entities.size(); i++) {
            distance = Math.sqrt(Math.pow(entities.get(i).posX - x, 2) + Math.pow(entities.get(i).posY - y, 2) + Math.pow(entities.get(i).posZ - z, 2));
            if (distance < closest) {
                closest = distance;
                closest_mob = i;
            }
        }
        angle = (Math.atan2(x - entities.get(closest_mob).posX, entities.get(closest_mob).posZ - z) * 180 / Math.PI) + 180;
        direction = (int) (Math.floor(angle / 45 + .5));
        switch (direction) {
            case 6: {
                compass = "WEST";
                break;
            }
            case 7: {
                compass = "NORTH-WEST";
                break;
            }
            case 8: {
                compass = "NORTH";
                break;
            }
            case 0: {
                compass = "NORTH";
                break;
            }
            case 1: {
                compass = "NORTH-EAST";
                break;
            }
            case 2: {
                compass = "EAST";
                break;
            }
            case 3: {
                compass = "SOUTH-EAST";
                break;
            }
            case 4: {
                compass = "SOUTH";
                break;
            }
            case 5: {
                compass = "SOUTH-WEST";
                break;
            }
        }
        player.sendMessage(new TextComponentString(TextFormatting.AQUA + "Nearest is " + round(closest) + " blocks away in the " + compass + " direction"));
        player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Elevation Difference: " + round(entities.get(closest_mob).posY - y)));
        return closest_mob;
    }
    @Override
    public void registerModels() {
        MainClass.proxy.registerItemRenderer(this,0,"inventory");
    }
    public void writeToNBT(NBTTagCompound compound, ItemStack stack){
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("mob type",0);
    }
}