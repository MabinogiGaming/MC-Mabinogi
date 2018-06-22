package com.mabinogi.lib.tile.iface;

import com.mabinogi.lib.network.NetworkHandler;
import com.mabinogi.lib.network.messages.MessageGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IGuiTile {

	public abstract String getInventoryName();
	
	public Object getGui(int id, EntityPlayer player, World world, int x, int y, int z);
	public Object getContainer(int id, EntityPlayer player, World world, int x, int y, int z);
	
	public NetworkHandler getNetworkHandler();
	public MessageGui writeMessageGui();
	
	public boolean isPlayerInRange(EntityPlayer player);

}
