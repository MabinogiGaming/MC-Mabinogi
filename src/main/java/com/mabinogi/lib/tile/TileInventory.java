package com.mabinogi.lib.tile;

import javax.annotation.Nullable;

import com.mabinogi.lib.network.messages.MessageGui;
import com.mabinogi.lib.util.FluidUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * Adds inventory storage to a tile.
 * Handles both item and fluid storage.
 * The methods #createItemInventory and #createFluidInventory will determine which storage is created.
 * 
 * @author Mabinogi
 */
public abstract class TileInventory extends TileBase implements IInventory, IFluidHandler {

	//a list of items stored by this inventory
	private NonNullList<ItemStack> items;
	
	//a list of fluid tanks stored by this inventory
	private NonNullList<FluidTank> tanks;
	
	public TileInventory()
	{
		super();
		
		items = createItemInventory();
		tanks = createFluidInventory();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        items = createItemInventory();
        if (!items.isEmpty()) 
    	{
    		ItemStackHelper.loadAllItems(nbt, items);
    	}
        
        tanks = createFluidInventory();
        if (!tanks.isEmpty())
        {
        	FluidUtil.loadAllTanks(nbt, tanks);
        }
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if (!items.isEmpty()) 
    	{
        	ItemStackHelper.saveAllItems(nbt, items, false);
    	}
        
        if (!tanks.isEmpty())
        {
        	FluidUtil.saveAllTanks(nbt, tanks, false);
        }

        return nbt;
    }
	
	@Override
	public void readMessageGui(NBTTagCompound nbt)
	{
		super.readMessageGui(nbt);
		
		tanks = createFluidInventory();
		if (!tanks.isEmpty())
        {
        	FluidUtil.loadAllTanks(nbt, tanks);
        }
	}
	
	@Override
	public MessageGui writeMessageGui()
	{
		MessageGui msg = super.writeMessageGui();
		if (!tanks.isEmpty())
        {
        	FluidUtil.saveAllTanks(msg.nbt, tanks, true);
        }
    	return msg;
	}
	
	public NonNullList<ItemStack> createItemInventory()
	{
		return NonNullList.create();
	}
	
	public NonNullList<FluidTank> createFluidInventory()
	{
		return NonNullList.create();
	}
	
	@Override
	public String getName()
	{
		return blockType.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() 
	{
		return false;
	}
	
	@Override
	public int getSizeInventory()
	{
		return items.size();
	}

	@Override
	public boolean isEmpty()
    {
        for (ItemStack itemstack : this.items)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return items.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
        ItemStack itemstack = ItemStackHelper.getAndSplit(items, index, count);
        if (!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		items.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return isPlayerInRange(player);
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return true;
	}
	
	
	
	
	
	
	
	

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
    	if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    	{
    		return !items.isEmpty();
    	}
    	if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
    	{
    		return !tanks.isEmpty();
    	}
    	return super.hasCapability(capability, facing);
    }
	
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new InvWrapper(this));
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
        	return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, facing);
    }	

    
    
    
    
    
    
    public FluidTank getTank(int i)
    {
    	if (tanks.size() <= i) return null;
    	return tanks.get(i);
    }
    
	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		IFluidTankProperties[] properties = new IFluidTankProperties[tanks.size()];
		for (int i = 0; i < tanks.size(); i++)
		{
			FluidTank tank = tanks.get(i);
			properties[i] = new FluidTankProperties(tank.getFluid(), tank.getCapacity(), tank.canFill(), tank.canDrain());
		}
		return properties;
	}

	public boolean canFill(int slot, FluidStack fluidStack)
	{
		return true;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		int amount = resource.amount;
		
		for (int i = 0; i < tanks.size(); i++)
		{
			if (amount == 0) break;
			
			if (canFill(i, resource))
			{
				amount = amount - tanks.get(i).fill(resource, doFill);
			}
		}
		return resource.amount - amount;
	}
	
	public int fillInternal(FluidStack resource, boolean doFill)
	{
		int amount = resource.amount;
		
		for (int i = 0; i < tanks.size(); i++)
		{
			if (amount == 0) break;
				
			amount = amount - tanks.get(i).fillInternal(resource, doFill);
		}
		return resource.amount - amount;
	}

	public boolean canDrain(int slot, int amount)
	{
		return true;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		FluidStack ret = null;
		
		for (int i = 0; i < tanks.size(); i++)
		{
			if (ret != null && ret.amount != 0) break;
			
			if (resource != null && canDrain(i, resource.amount))
			{
				ret = tanks.get(i).drain(resource, doDrain);
			}
		}
		return ret;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		FluidStack ret = null;
		
		for (int i = 0; i < tanks.size(); i++)
		{
			if (ret != null && ret.amount != 0) break;
			
			if (canDrain(i, maxDrain))
			{
				ret = tanks.get(i).drain(maxDrain, doDrain);
			}
		}
		return ret;
	}
	
	public FluidStack drainInternal(int maxDrain, boolean doDrain)
	{
		FluidStack ret = null;
		
		for (int i = 0; i < tanks.size(); i++)
		{
			if (ret != null && ret.amount != 0) break;
			
			ret = tanks.get(i).drain(maxDrain, doDrain);
		}
		return ret;
	}

}
