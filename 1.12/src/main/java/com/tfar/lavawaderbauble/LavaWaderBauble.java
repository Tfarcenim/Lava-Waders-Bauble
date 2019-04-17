package com.tfar.lavawaderbauble;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = LavaWaderBauble.MODID, name = LavaWaderBauble.NAME, version = LavaWaderBauble.VERSION)
public class LavaWaderBauble
{
    public static final String MODID = "lavawaderbauble";
    public static final String NAME = "Lava Wader Bauble";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MODID)
    public static LavaWaderBauble instance;

    private static Logger logger;
    @SidedProxy(clientSide = "com.tfar."+MODID+".ClientProxy", serverSide = "com.tfar."+MODID+".CommonProxy")
    public static CommonProxy proxy;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        ModItems.load();
        proxy.registerModels();
        MinecraftForge.EVENT_BUS.register(new LavaWaderBaubleEventHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
