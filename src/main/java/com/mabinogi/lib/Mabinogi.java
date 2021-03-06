package com.mabinogi.lib;

import com.mabinogi.lib.logging.LogHandler;
import com.mabinogi.lib.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = MabinogiSettings.MODID, name = MabinogiSettings.NAME, version = MabinogiSettings.VERSION)
public class Mabinogi
{
	@Instance
    public static Mabinogi instance;
    
    @SidedProxy(clientSide = "com.mabinogi.lib.proxy.ClientProxy", serverSide = "com.mabinogi.lib.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    public static LogHandler LOG;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
    	//initialise logging
        LOG = new LogHandler(event.getModLog(), LogHandler.LEVEL_WARN);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	//register gui proxy
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }
    
    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {

    }
    
    @EventHandler
    public void complete(FMLLoadCompleteEvent event)
    {

    }
}
