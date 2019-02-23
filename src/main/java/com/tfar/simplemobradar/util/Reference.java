package com.tfar.simplemobradar.util;

import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.ArrayList;
import java.util.List;

public class Reference {

        public static final String MOD_ID = "simplemobradar";
        public static final String NAME = "Simple Mob Radar";
        public static final String VERSION = "1.2";
        public static final String MC_VERSION = "1.12.2,";
        public static final String CLIENT_PROXY_CLASS = "com.tfar.simplemobradar.proxy.ClientProxy";
        public static final String COMMON_PROXY_CLASS = "com.tfar.simplemobradar.proxy.CommonProxy";
        public static List<EntityEntry> valid = new ArrayList<>();
        public static List<String> mobs = new ArrayList<>();
        public static List<Class> mob_class = new ArrayList<>();
    }
