package com.tfar.simplemobradar;

import com.tfar.simplemobradar.proxy.CommonProxy;
import com.tfar.simplemobradar.util.Reference;

import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class MainClass {

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static Logger logger = LogManager.getLogger("simplemobradar");

    @EventHandler
    public void init(FMLInitializationEvent event) {
      //add hostiles
        for (EntityEntry e : ForgeRegistries.ENTITIES)
            if (IMob.class.isAssignableFrom(e.getEntityClass())) {Reference.valid_mobs.add(e);}
        for (EntityEntry mob : Reference.valid_mobs) {
            Reference.mob_class.add(mob.getEntityClass());
        }
        for (EntityEntry mob : Reference.valid_mobs) {
            Reference.mobs.add(mob.getName());}
        //add passives
            for (EntityEntry e : ForgeRegistries.ENTITIES)
                if (EntityAnimal.class.isAssignableFrom(e.getEntityClass())) {Reference.valid_animals.add(e);}
            for (EntityEntry mob : Reference.valid_animals) {
                Reference.animal_class.add(mob.getEntityClass());
            }
            for (EntityEntry mob : Reference.valid_animals) {Reference.animals.add(mob.getName());}

        }
    }


