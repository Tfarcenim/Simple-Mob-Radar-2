package com.tfar.simplemobradar;

import com.tfar.simplemobradar.init.EntityList;
import com.tfar.simplemobradar.network.NetworkHandler;
import com.tfar.simplemobradar.proxy.CommonProxy;
import com.tfar.simplemobradar.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class MainClass {

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    public static Logger logger = LogManager.getLogger("simplemobradar");
    public static final SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper(Reference.MOD_ID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerEvents();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NETWORK_WRAPPER.registerMessage(NetworkHandler.Handler.class, NetworkHandler.class, 0, Side.SERVER);


        EntityList.init2();

    }
    }


