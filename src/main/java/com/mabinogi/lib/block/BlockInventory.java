package com.mabinogi.lib.block;

import java.util.List;

import javax.annotation.Nullable;

import com.mabinogi.lib.tile.TileInventory;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockInventory extends BlockTile {

	public BlockInventory(Material material)
	{
		super(material);
	}
	
	public boolean keepInventory()
	{
		return false;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		//do nothing, retain inventory
		if (!keepInventory())
		{
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
		}
	}
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if (keepInventory())
		{
			if (tileentity instanceof TileInventory)
	        {
	        	TileInventory tileInventory = (TileInventory) tileentity;
	        	
	        	ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));

	        	NBTTagCompound nbttagcompound = new NBTTagCompound();
	            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
	            nbttagcompound.setTag("BlockEntityTag", tileInventory.writeToNBT(nbttagcompound1));
	            itemstack.setTagCompound(nbttagcompound);

	            spawnAsEntity(worldIn, pos, itemstack);
	        }
		}
		else
		{
			if (tileentity instanceof IInventory)
	        {
	            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
	        }
	        super.breakBlock(worldIn, pos, state);
		}

        super.breakBlock(worldIn, pos, state);
    }
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        super.addInformation(stack, player, tooltip, advanced);
        
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        if (nbttagcompound != null && nbttagcompound.hasKey("BlockEntityTag", 10))
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");

            if (nbttagcompound1.hasKey("Items", 9))
            {
                NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(27, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(nbttagcompound1, nonnulllist);
                int i = 0;
                int j = 0;

                for (ItemStack itemstack : nonnulllist)
                {
                    if (!itemstack.isEmpty())
                    {
                        ++j;

                        if (i <= 4)
                        {
                            ++i;
                            tooltip.add(String.format("%s x%d", itemstack.getDisplayName(), itemstack.getCount()));
                        }
                    }
                }

                if (j - i > 0)
                {
                    tooltip.add(String.format(TextFormatting.ITALIC + I18n.format("container.shulkerBox.more"), j - i));
                }
            }
        }
    }

}
