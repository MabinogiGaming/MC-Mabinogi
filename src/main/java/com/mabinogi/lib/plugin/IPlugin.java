package com.mabinogi.lib.plugin;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IPlugin {
	
	public int getPriority();
	
	public void start();
	
    public void pre(FMLPreInitializationEvent event);
    
    public void init(FMLInitializationEvent event);
    
    public void post(FMLPostInitializationEvent event);
    
    public void complete(FMLLoadCompleteEvent event);

}
