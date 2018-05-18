package com.mabinogi.lib.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockTile extends BlockBase implements ITileEntityProvider {

	public BlockTile(Material material)
	{
		super(material);
	}
	
	public abstract Class<? extends TileEntity> getTileEntityClass();
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		try
		{
			return getTileEntityClass().newInstance();
		} 
		catch (InstantiationException | IllegalAccessException e)
		{
			return null;
		}
	}

}
