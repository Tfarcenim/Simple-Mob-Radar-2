package com.tfar.simplemobradar.proxy;

import com.tfar.simplemobradar.MainClass;
import com.tfar.simplemobradar.config.EventConfigChanged;
import com.tfar.simplemobradar.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy{public void registerItemRenderer(Item item, int meta, String id) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));}

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);

    EventConfigChanged eventConfigChanged = new EventConfigChanged();
    MinecraftForge.EVENT_BUS.register(eventConfigChanged);
}    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Reference.MOD_ID))
        {
            MainClass.proxy.onConfigChanged(event);
        }
    }
}
