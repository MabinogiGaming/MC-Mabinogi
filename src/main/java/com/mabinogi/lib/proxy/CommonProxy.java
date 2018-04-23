package com.mabinogi.lib.proxy;

import com.mabinogi.lib.block.BlockBase;
import com.mabinogi.lib.item.ItemBase;
import com.mabinogi.lib.registry.RegistryManager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) 
    {
    	for (Item item : RegistryManager.instance.getRegistryItems())
    	{
    		//add item to game registry
    		event.getRegistry().register(item);
    		
    		//register ore name if required
    		registerOre(item);
    	}
    }
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) 
	{
		for (Block block : RegistryManager.instance.getRegistryBlocks())
    	{
			//add block to game registry
    		event.getRegistry().register(block);
    	}
    }
	
	/**
	 * Registers the ore dictionary name for a provided item
	 * @param item The item
	 */
	public static void registerOre(Item item)
	{
		if (item instanceof ItemBase)
		{
			if (((ItemBase) item).getOreName() != null)
			{
				OreDictionary.registerOre(((ItemBase) item).getOreName(), item);
			}
		}
		else if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockBase)
		{
			if (((BlockBase) ((ItemBlock) item).getBlock()).getOreName() != null)
			{
				OreDictionary.registerOre(((BlockBase) ((ItemBlock) item).getBlock()).getOreName(), item);
			}
		}
	}

}
