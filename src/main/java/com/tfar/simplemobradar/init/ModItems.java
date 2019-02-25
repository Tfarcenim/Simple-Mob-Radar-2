package com.tfar.simplemobradar.init;

import com.tfar.simplemobradar.items.ItemSimpleMobRadar;
import com.tfar.simplemobradar.items.ItemSimplePassiveRadar;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final Item SIMPLE_MOB_RADAR = new ItemSimpleMobRadar("simplemobradar");
    public static final Item SIMPLE_PASSIVE_RADAR = new ItemSimplePassiveRadar("simplepassiveradar");

}
