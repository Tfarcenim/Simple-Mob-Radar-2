package com.tfar.simplemobradar.init;

import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Collections;
import java.util.Comparator;

import static com.tfar.simplemobradar.util.Reference.animals;
import static com.tfar.simplemobradar.util.Reference.mobs;

public class EntityList {
    public static void init2() {

        for (EntityEntry e : ForgeRegistries.ENTITIES) {
            if (IMob.class.isAssignableFrom(e.getEntityClass()))
                mobs.add(e);

            if (EntityAnimal.class.isAssignableFrom(e.getEntityClass()))
                animals.add(e);

            Collections.sort(mobs, Comparator.comparing(EntityEntry::getName));
            Collections.sort(animals, Comparator.comparing(EntityEntry::getName));
        }
    }
}