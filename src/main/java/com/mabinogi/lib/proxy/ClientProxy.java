package com.mabinogi.lib.proxy;

import com.mabinogi.lib.registry.RegistryManager;
import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		if (tile != null && tile instanceof IGuiTile)
        {
        	return ((IGuiTile) tile).getGui(id, player, world, x, y, z);
        }
        else
        {
            return null;
        }
	}

	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) 
	{
		for (Item item : RegistryManager.instance.getRegistryItems())
    	{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    	}
    }
	
}
