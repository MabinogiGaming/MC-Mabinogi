package com.mabinogi.lib.gui.container;

import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
	
	/**
     * Called when a player shift-clicks on a slot.
     */
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        Slot slot = this.inventorySlots.get(index);
        return slot != null ? slot.getStack() : ItemStack.EMPTY;
    }

    /*public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
        if (tile == null) return;
        	
        for (int i = 0; i < listeners.size(); i++)
		{
			tile.sendUpdateMessageGUI(this, (IContainerListener) listeners.get(i));
		}
    }*/

}
