package com.mabinogi.lib.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mabinogi.lib.model.AbstractItemModel;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class RegistryManager {
	
	public static RegistryManager instance = new RegistryManager(); 
	
	private List<Item> registryItems = new ArrayList<>();
	private List<Block> registryBlocks = new ArrayList<>();
	
	private Map<ModelResourceLocation, AbstractItemModel> registryItemModels = Maps.newHashMap();
	
	public void registerItem(Item item)
	{
		registryItems.add(item);
	}
	
	public void registerBlock(Block block)
	{
		registryBlocks.add(block);
	}

	public void registerItemModel(ModelResourceLocation location, AbstractItemModel model)
	{
		this.registryItemModels.put(location, model);
	}

	public List<Item> getRegistryItems()
	{
		return registryItems;
	}
	
	public List<Block> getRegistryBlocks()
	{
		return registryBlocks;
	}
	
	public Map<ModelResourceLocation, AbstractItemModel> getItemModels()
	{
		return registryItemModels;
	}

}
