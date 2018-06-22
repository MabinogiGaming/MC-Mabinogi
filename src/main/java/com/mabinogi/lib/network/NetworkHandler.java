package com.mabinogi.lib.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class NetworkHandler {
	
	//network channel
	public SimpleNetworkWrapper network;
	
	public NetworkHandler() 
	{
		network = NetworkRegistry.INSTANCE.newSimpleChannel(getMod());
	}
	
	public abstract String getMod();
	
	//register messages here
	public abstract void init();

}
