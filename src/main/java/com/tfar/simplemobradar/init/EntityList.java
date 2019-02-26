package com.tfar.simplemobradar.init;

import com.tfar.simplemobradar.util.Reference;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;

public class EntityList {
    public static void init2() {
        List<EntityEntry> valid_mobs = new ArrayList<>();
        List<String> mobs = new ArrayList<>();
        List<Class<? extends Entity>> mob_class = new ArrayList<>();

        List<EntityEntry> valid_animals = new ArrayList<>();
        List<String> animals = new ArrayList<>();
        List<Class<? extends Entity>> animal_class = new ArrayList<>();

        //add hostiles
        for (EntityEntry e : ForgeRegistries.ENTITIES)
            if (IMob.class.isAssignableFrom(e.getEntityClass())) {
                valid_mobs.add(e);
                mob_class.add(e.getEntityClass());
                mobs.add(I18n.format("entity." + e.getName() + ".name"));
            }
        //add passives
        for (EntityEntry e : ForgeRegistries.ENTITIES)
            if (EntityAnimal.class.isAssignableFrom(e.getEntityClass())) {
                valid_animals.add(e);
                animal_class.add(e.getEntityClass());
                animals.add(I18n.format("entity." + e.getName() + ".name"));
            }
        Map<String, Class<? extends Entity>> sortmobs = new HashMap<>();
        Map<String, Class<? extends Entity>> sortanimals = new HashMap<>();

        for (int i = 0; i < valid_mobs.size(); i++) {
            sortmobs.put(mobs.get(i), mob_class.get(i));
        }
        for (int i = 0; i < valid_animals.size(); i++) {
            sortanimals.put(animals.get(i), animal_class.get(i));
        }

        //sort the HashMap by sticking it in a TreeMap
        TreeMap<String, Class<? extends Entity>> sortedmobs = new TreeMap<>(sortmobs);
        TreeMap<String, Class<? extends Entity>> sortedanimals = new TreeMap<>(sortanimals);

        //construct a new list that is sorted
        for (Map.Entry<String, Class<? extends Entity>> entry : sortedmobs.entrySet()) {
            Reference.sorted_mobs.add(entry.getKey());
            Reference.sorted_mob_class.add(entry.getValue());
        }

        for (Map.Entry<String, Class<? extends Entity>> entry : sortedanimals.entrySet()) {
            Reference.sorted_animals.add(entry.getKey());
            Reference.sorted_animal_class.add(entry.getValue());
        }
    }
}