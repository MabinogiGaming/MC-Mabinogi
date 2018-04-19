package com.mabinogi.lib;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Mabinogi.MODID, name = Mabinogi.NAME, version = Mabinogi.VERSION)
public class Mabinogi
{
    public static final String MODID = "mabinogi";
    public static final String NAME = "Mabinogi";
    public static final String VERSION = "0.0.0";
    
    public static Logger LOG;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	//initialise logging
        LOG = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
