package com.mabinogi.lib.proxy;

import com.mabinogi.lib.block.BlockBase;
import com.mabinogi.lib.block.BlockTile;
import com.mabinogi.lib.item.ItemBase;
import com.mabinogi.lib.registry.RegistryManager;
import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		if (tile != null && tile instanceof IGuiTile)
        {
            return ((IGuiTile) tile).getContainer(id, player, world, x, y, z);
        }
        else
        {
            return null;
        }
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) 
    {
    	for (Item item : RegistryManager.instance.getRegistryItems())
    	{
    		//add item to game registry
    		event.getRegistry().register(item);
    		
    		//register ore name if required
    		registerOre(item);
    	}
    }
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) 
	{
		for (Block block : RegistryManager.instance.getRegistryBlocks())
    	{
			//add block to game registry
    		event.getRegistry().register(block);
    		
    		//register tile
    		if (block instanceof BlockTile)
    		{
    			GameRegistry.registerTileEntity(((BlockTile) block).getTileEntityClass(), ((BlockTile) block).getModId() + ":" + ((BlockTile) block).getName());
    		}
    	}
    }
	
	/**
	 * Registers the ore dictionary name for a provided item
	 * @param item The item
	 */
	public static void registerOre(Item item)
	{
		if (item instanceof ItemBase)
		{
			if (((ItemBase) item).getOreName() != null)
			{
				OreDictionary.registerOre(((ItemBase) item).getOreName(), item);
			}
		}
		else if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockBase)
		{
			if (((BlockBase) ((ItemBlock) item).getBlock()).getOreName() != null)
			{
				OreDictionary.registerOre(((BlockBase) ((ItemBlock) item).getBlock()).getOreName(), item);
			}
		}
	}

}
