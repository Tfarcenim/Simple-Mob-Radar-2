package com.tfar.simplemobradar.config;

import com.tfar.simplemobradar.util.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MOD_ID)
public class ConfigHandler {
    @Config.Name("Range of mob radar in blocks")
    public static int RANGE_MOBS = 64;
    @Config.Name("Range of passive radar in blocks")
    public static int RANGE_ANIMALS = 150;
    @Config.Name("Display total nearby mobs?")
    public static boolean DISPLAY_TOTAL_MOBS = true;
    @Config.Name("Display elevation difference?")
    public static boolean DISPLAY_ELEVATION = true;
    @Config.Name("Display mob distance?")
    public static boolean DISPLAY_DISTANCE = true;
}

