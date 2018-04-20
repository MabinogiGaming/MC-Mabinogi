package com.mabinogi.lib.plugin;

import java.util.Comparator;
import java.util.TreeSet;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class PluginManager {
	
	Comparator<IPlugin> comparator = new Comparator<IPlugin>() 
    {
        @Override
        public int compare(IPlugin a, IPlugin b) 
        {
            Integer pA = a.getPriority();
            Integer pB = b.getPriority();
            
            if (pA == pB) pA++;
            
            return pA.compareTo(pB);
        }
    };
    public TreeSet<IPlugin> plugins = new TreeSet<IPlugin>(comparator);
	
	public abstract void registerPlugin(IPlugin plugin);
	
	public void start()
    {
    	for (IPlugin plugin : plugins)
    	{
    		plugin.start();
    	}
    }
	
    public void pre(FMLPreInitializationEvent event)
    {
    	for (IPlugin plugin : plugins)
    	{
    		plugin.pre(event);
    	}
    }
    
    public void init(FMLInitializationEvent event)
    {
    	for (IPlugin plugin : plugins)
    	{
    		plugin.init(event);
    	}
    }
    
    public void post(FMLPostInitializationEvent event)
    {
    	for (IPlugin plugin : plugins)
    	{
    		plugin.post(event);
    	}
    }
    
    public void complete(FMLLoadCompleteEvent event)
    {
    	for (IPlugin plugin : plugins)
    	{
    		plugin.complete(event);
    	}
    }

}
