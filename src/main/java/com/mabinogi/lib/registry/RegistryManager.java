package com.mabinogi.lib.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegistryManager {
	
	public static RegistryManager instance = new RegistryManager(); 
	
	private List<Item> registryItems = new ArrayList<>();
	private List<Block> registryBlocks = new ArrayList<>();
	
	public void registerItem(Item item)
	{
		registryItems.add(item);
	}
	
	public void registerBlock(Block block)
	{
		registryBlocks.add(block);
	}
	
	public List<Item> getRegistryItems()
	{
		return registryItems;
	}
	
	public List<Block> getRegistryBlocks()
	{
		return registryBlocks;
	}

}
