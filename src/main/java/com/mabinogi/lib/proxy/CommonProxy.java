package com.mabinogi.lib.proxy;

import com.mabinogi.lib.registry.RegistryManager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) 
    {
    	for (Item item : RegistryManager.instance.getRegistryItems())
    	{
    		event.getRegistry().register(item);
    	}
    }
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) 
	{
		
    }

}
