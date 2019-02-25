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

public class ItemSimplePassiveRadar extends Item implements IHasModel {
    public ItemSimplePassiveRadar(String name) {
        this.setTranslationKey(name);
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        this.setCreativeTab(CreativeTabs.TOOLS);
        ModItems.ITEMS.add(this);
    }

    private static int r = ConfigHandler.RANGE_ANIMALS;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        BlockPos pos = player.getPosition();
        if (!world.isRemote && !player.isSneaking()) {
            player.getCooldownTracker().setCooldown(this, 20);
            int size = getMobs(world, pos, player, hand);
            player.getHeldItem(hand).getTagCompound().setInteger("Size", size);
            if (size > 0) {
                player.getHeldItem(hand).getTagCompound().setInteger("State", 1);
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

    public int getMobs(World Worldin, BlockPos pos, EntityPlayer player, EnumHand hand) {

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        List<Entity> entities = Worldin.getEntitiesWithinAABB(Reference.animal_class.get(player.getHeldItem(hand).getTagCompound().getInteger("mob type")), new AxisAlignedBB(x - r, y - r, z - r, x + r, y + r, z + r));
        if (entities.size() == 0) return 0;
        int closest_mob = 0;
        double distance;
        double closest = 2000000000;
        for (int i = 0; i < entities.size(); i++) {
            ((EntityLivingBase) entities.get(i)).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 1));
            distance = player.getDistance(entities.get(i).posX, entities.get(i).posY, entities.get(i).posZ);
            if (distance < closest) {
                closest = distance;
                closest_mob = i;
            }
        }
        player.getHeldItem(hand).getTagCompound().setDouble("X position", entities.get(closest_mob).posX);
        player.getHeldItem(hand).getTagCompound().setDouble("Y position", entities.get(closest_mob).posY);
        player.getHeldItem(hand).getTagCompound().setDouble("Z position", entities.get(closest_mob).posZ);
        return entities.size();
    }

    @Override
    public void registerModels() {
        MainClass.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public static void writeToNBT(ItemStack stack) {
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("mob type", 0);
        stack.getTagCompound().setInteger("State", 2);
        stack.getTagCompound().setInteger("Size", 0);
    }

    public int getState(ItemStack stack) {
        return stack.getTagCompound().getInteger("State");
    }
}