package com.mabinogi.lib.block;

import com.mabinogi.lib.registry.RegistryManager;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

/**
 * Abstract class used to create and register blocks 
 * @author Mabinogi
 */
public abstract class BlockAbstract extends Block {
	
	public BlockAbstract(Material material)
	{
		super(material);
	}
	
	public abstract String getName();
	public abstract String getModId();
	public abstract CreativeTabs getTab();
	
	/**
	 * The ore dictionary to be registered to this block, override if required
	 * @return The ore dictionary name
	 */
	public String getOreName()
	{
		return null;
	}
	
	/**
	 * Helper method used to register a blockbase, also handles the itemblock
	 * @param block The blockbase to be registered
	 * @return The created blockbase, so that it can be stored
	 */
	public static <T extends BlockAbstract> T register(T block) 
	{
		//register names
		block.setRegistryName(block.getName());
		block.setUnlocalizedName(block.getModId() + "." + block.getName());
		
		//set creative tab
		if (block.getTab() != null) block.setCreativeTab(block.getTab()); 
		
		//create itemblock
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		
		//register item block
		RegistryManager.instance.registerItem(itemBlock);
		
		//register block
		RegistryManager.instance.registerBlock(block);

		return block;
	}

}
