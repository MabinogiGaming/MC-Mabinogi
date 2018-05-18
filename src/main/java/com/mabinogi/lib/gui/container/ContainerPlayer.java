package com.mabinogi.lib.gui.container;

import java.util.List;

import com.google.common.collect.Lists;
import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerPlayer extends ContainerBase {
	
	public List<Slot> swapSlots = Lists.<Slot>newArrayList();

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
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
    {
    	ItemStack newStack = null;
        Slot slot = (Slot) inventorySlots.get(slotNumber);
        
        //int invSize = (tile == null || ((tile instanceof TileInventory)) ? 0 : tile.getSizeInventory();
        int invSize = 0;
        int playerSize = invSize + 36;
    	
        if (slot != null && slot.getHasStack())
        {
        	ItemStack stack = slot.getStack();
            newStack = stack.copy();
            
            if (slotNumber < invSize)
            {
            	if (!mergeItemStack(stack, invSize, playerSize, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(stack, newStack);
            }
            else
            {
            	/*if (tile instanceof TileInventory)
            	{
	            	for (int i = 0; i < invSize; i++)
	            	{
	            		if (tile.isItemValidForSlot(i, stack))
	                    {
	            			if (!mergeItemStack(stack, i, i + 1, false))
	            			{
								return ItemStack.EMPTY;	            			
	            			}
	                    }
	            	}
            	}*/
            }

            if (stack.isEmpty())
            {
            	slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
            
            if (stack.getCount() == newStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }
    	
        return newStack;
    }

}
