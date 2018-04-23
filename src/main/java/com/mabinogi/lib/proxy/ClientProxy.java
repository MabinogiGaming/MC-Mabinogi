package com.mabinogi.lib.proxy;

import com.mabinogi.lib.registry.RegistryManager;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) 
	{
		for (Item item : RegistryManager.instance.getRegistryItems())
    	{
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    	}
    }
	
}
