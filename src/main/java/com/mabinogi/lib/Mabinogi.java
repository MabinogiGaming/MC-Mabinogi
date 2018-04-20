package com.mabinogi.lib;

import com.mabinogi.lib.logging.LogHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Mabinogi.MODID, name = Mabinogi.NAME, version = Mabinogi.VERSION)
public class Mabinogi
{
    public static final String MODID = "mabinogi";
    public static final String NAME = "Mabinogi";
    public static final String VERSION = "0.0.2";
    
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
