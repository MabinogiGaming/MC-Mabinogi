package com.mabinogi.lib.tile.iface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IGuiTile {

	public abstract String getInventoryName();
	
	public Object getGui(int id, EntityPlayer player, World world, int x, int y, int z);
	public Object getContainer(int id, EntityPlayer player, World world, int x, int y, int z);
	
	public boolean isPlayerInRange(EntityPlayer player);

}
