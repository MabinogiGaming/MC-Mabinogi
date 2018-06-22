package com.mabinogi.lib.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FluidUtil {
	
	public static boolean isFluidItemHandler(ItemStack stack) 
	{
		return !stack.isEmpty() && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
	}
	
	public static boolean fluidItemInteract(ItemStack stack, IFluidHandler handler, EntityPlayer player, EnumHand hand) 
	{
		return fluidItemFill(stack, handler, player, hand) || fluidItemDrain(stack, handler, player, hand);
	}
	
	public static boolean fluidItemFill(ItemStack stack, IFluidHandler handler, EntityPlayer player, EnumHand hand) 
	{
		if (stack.isEmpty() || handler == null || player == null) 
		{
			return false;
		}
		
		FluidActionResult result = net.minecraftforge.fluids.FluidUtil.tryFillContainerAndStow(stack, handler, new InvWrapper(player.inventory), Integer.MAX_VALUE, player, true);
		if (result.isSuccess()) 
		{
			player.setHeldItem(hand, result.getResult());
			return true;
		}
		return false;
	}
	
	public static boolean fluidItemDrain(ItemStack stack, IFluidHandler handler, EntityPlayer player, EnumHand hand) 
	{
		if (stack.isEmpty() || handler == null || player == null) 
		{
			return false;
		}
		
		FluidActionResult result = net.minecraftforge.fluids.FluidUtil.tryEmptyContainerAndStow(stack, handler, new InvWrapper(player.inventory), Integer.MAX_VALUE, player, true);
		if (result.isSuccess()) 
		{
			player.setHeldItem(hand, result.getResult());
			return true;
		}
		return false;
	}
	
	public static NBTTagCompound saveAllTanks(NBTTagCompound tag, NonNullList<FluidTank> list)
    {
        return saveAllTanks(tag, list, true);
    }
	
	public static NBTTagCompound saveAllTanks(NBTTagCompound tag, NonNullList<FluidTank> list, boolean saveEmpty)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < list.size(); ++i)
        {
            FluidTank fluidTank = list.get(i);

            if (!(fluidTank.getFluid() == null))
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                fluidTank.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        if (!nbttaglist.hasNoTags() || saveEmpty)
        {
            tag.setTag("Tanks", nbttaglist);
        }

        return tag;
    }

    public static void loadAllTanks(NBTTagCompound tag, NonNullList<FluidTank> list)
    {
        NBTTagList nbttaglist = tag.getTagList("Tanks", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < list.size())
            {
            	list.get(j).readFromNBT(nbttagcompound);
            }
        }
    }

}
