package com.tfar.simplemobradar.util;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.ArrayList;
import java.util.List;

public class Reference {

        public static final String MOD_ID = "simplemobradar";
        public static final String NAME = "Simple Mob Radar";
        public static final String VERSION = "2.0";
        public static final String MC_VERSION = "1.12.2,";
        public static final String CLIENT_PROXY_CLASS = "com.tfar.simplemobradar.proxy.ClientProxy";
        public static final String COMMON_PROXY_CLASS = "com.tfar.simplemobradar.proxy.CommonProxy";

        public static List<EntityEntry> valid_mobs = new ArrayList<>();
        public static List<String> mobs = new ArrayList<>();
        public static List<Class<? extends Entity>> mob_class = new ArrayList<>();

        public static List<EntityEntry> valid_animals = new ArrayList<>();
        public static List<String> animals = new ArrayList<>();
        public static List<Class<? extends Entity>> animal_class = new ArrayList<>();
}
