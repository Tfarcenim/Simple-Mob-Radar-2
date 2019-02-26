package com.tfar.simplemobradar.init;

import com.tfar.simplemobradar.util.Reference;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityList {
    public static void init2() {
//add hostiles
        for (EntityEntry e : ForgeRegistries.ENTITIES)
            if (IMob.class.isAssignableFrom(e.getEntityClass())) {
                Reference.valid_mobs.add(e);
                Reference.mob_class.add(e.getEntityClass());
                Reference.mobs.add(e.getName());
            }
        //add passives
        for (EntityEntry e : ForgeRegistries.ENTITIES)
            if (EntityAnimal.class.isAssignableFrom(e.getEntityClass())) {
                Reference.valid_animals.add(e);
                Reference.animal_class.add(e.getEntityClass());
                Reference.animals.add(e.getName());
            }
    }
}
