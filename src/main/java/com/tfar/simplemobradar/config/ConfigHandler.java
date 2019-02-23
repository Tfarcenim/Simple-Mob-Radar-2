package com.tfar.simplemobradar.config;

import com.tfar.simplemobradar.util.Reference;
import net.minecraftforge.common.config.Config;
@Config(modid = Reference.MOD_ID)
public class ConfigHandler {
    @Config.Name("Range in Blocks")
    public static int range = 64;
}
