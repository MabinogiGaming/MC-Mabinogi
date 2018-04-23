package com.mabinogi.lib.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class RegistryManager {
	
	public static RegistryManager instance = new RegistryManager(); 
	
	private List<Item> registryItems = new ArrayList<>();
	
	public void registerItem(Item item)
	{
		registryItems.add(item);
	}
	
	public List<Item> getRegistryItems()
	{
		return registryItems;
	}

}
