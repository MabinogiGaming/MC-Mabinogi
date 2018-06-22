package com.mabinogi.lib.gui.container;

import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public abstract class ContainerBase extends Container {
	
	protected IGuiTile tile;
	
	public ContainerBase(InventoryPlayer inventory, IGuiTile tile) 
	{
		this.tile = tile;
        
		//create custom slots
		addSlots(inventory);
	}
	
	public abstract int getGuiWidth();
	public abstract int getGuiHeight();
	
	public abstract void addSlots(InventoryPlayer inventory);

	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return tile.isPlayerInRange(player);
	}

	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
        if (tile == null) return;
        	
        for (int i = 0; i < listeners.size(); i++)
		{
        	if (listeners.get(i) instanceof EntityPlayerMP)
    		{
    			tile.getNetworkHandler().network.sendTo(tile.writeMessageGui(), (EntityPlayerMP) listeners.get(i));
    		}
		}
    }

}
