package com.mabinogi.lib.gui.container;

import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerPlayer extends ContainerBase {

	public ContainerPlayer(InventoryPlayer inventory, IGuiTile tile)
	{
		super(inventory, tile);
		
		//create standard inventory and player bar
		addInventory(inventory);
		addPlayer(inventory);
	}
	
	public void addInventory(InventoryPlayer inventory)
	{
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
            	this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, getGuiHeight() - 28 - ((3 - i) * 18)));
            }
        }
	}
	
	public void addPlayer(InventoryPlayer inventory)
	{
        for (int i = 0; i < 9; ++i)
        {
        	this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, getGuiHeight() - 24));
        }
	}
	
	/**
     * Called when a player shift-clicks on a slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
    	int numRows = 3;
    	
    	ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, numRows * 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, numRows * 9, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

}
