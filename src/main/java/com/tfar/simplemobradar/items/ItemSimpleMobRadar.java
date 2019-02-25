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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemSimpleMobRadar extends Item implements IHasModel {
    public ItemSimpleMobRadar(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        this.setCreativeTab(CreativeTabs.MISC);
        ModItems.ITEMS.add(this);
    }

    private static int r = ConfigHandler.RANGE_MOBS;
    private List<Entity> entityList;

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
            player.getHeldItem(hand).getTagCompound().setInteger("Size", entityList.size());
            if (entityList != null && entityList.size() > 0) {
                player.getHeldItem(hand).getTagCompound().setInteger("State", 1);
                getClosestMobs(player, hand);
                //((EntityLivingBase) entityList.get(saved_mob)).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 1));
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

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        return Worldin.getEntitiesWithinAABB(Reference.mob_class.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type")), new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
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

    public void getClosestMobs(EntityPlayer player, EnumHand hand) {
        List<Entity> entities = entityList;
        int closest_mob = 0;
        double distance;
        double closest = 2000000000;
        for (int i = 0; i < entities.size(); i++) {
            ((EntityLivingBase) entityList.get(i)).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 1));
            distance = player.getDistance(entities.get(i).posX, entities.get(i).posY, entities.get(i).posZ);
            if (distance < closest) {
                closest = distance;
                closest_mob = i;
            }
        }

        player.getHeldItem(hand).getTagCompound().setDouble("X position", entities.get(closest_mob).posX);
        player.getHeldItem(hand).getTagCompound().setDouble("Y position", entities.get(closest_mob).posY);
        player.getHeldItem(hand).getTagCompound().setDouble("Z position", entities.get(closest_mob).posZ);
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
        return stack.getTagCompound().getInteger("State");
    }
}