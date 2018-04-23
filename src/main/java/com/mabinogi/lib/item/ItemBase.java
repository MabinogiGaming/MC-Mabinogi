package com.mabinogi.lib.item;

import com.mabinogi.lib.registry.RegistryManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public abstract class ItemBase extends Item {
	
	public abstract String getName();
	public abstract String getModId();
	public abstract CreativeTabs getTab();
	
	public static <T extends ItemBase> T register(T item)
	{
		//register names
		item.setRegistryName(item.getName());
		item.setUnlocalizedName(item.getModId() + "." + item.getName());
		
		//set creative tab
		if (item.getTab() != null) item.setCreativeTab(item.getTab()); 
		
		//register item
		RegistryManager.instance.registerItem(item);
		
		return item;
	}

}
