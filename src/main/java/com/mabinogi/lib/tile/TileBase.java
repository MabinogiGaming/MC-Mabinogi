package com.mabinogi.lib.tile;

import com.mabinogi.lib.network.NetworkHandler;
import com.mabinogi.lib.network.messages.MessageGui;
import com.mabinogi.lib.network.messages.MessageTile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * The base of a TileEntity.
 * Doesn't do much by itself, mostly handles network messages and helper functions.
 * 
 * @author Mabinogi
 */
public abstract class TileBase extends TileEntity {
	
	public TileBase()
    {
		super();
    }
	
	/* NETWORK 																	 			*/
	/*																						*/
	
	/**
	 * Returns the network handler responsible for handling network messages.
	 * @return The NetworkHandler.
	 */
	public abstract NetworkHandler getNetworkHandler();
	
	/**
	 * This is called when a dirty block requests an update from the server.
	 * Create a MessageTile that has updated information.
	 */
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		MessageTile msg = writeMessageTile();
        return new SPacketUpdateTileEntity(msg.blockPos, 0, msg.nbt);
	}
	
	@Override
	/**
	 * This is called when a dirty block recieves an update from the server.
	 * Read the message tile and update the blocks information.
	 */
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) 
    {
        readMessageTile(packet.getNbtCompound());
        
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
        world.checkLight(pos);
    }
	
	
	/* NETWORK TILE																 			*/
	/* Inform the client that a block has changed appearance in the world.					*/
	
	/**
	 * Updates a tile using the data specified.
	 * @param nbt The nbt containing updates.
	 */
	public void readMessageTile(NBTTagCompound nbt)
	{
		//nothing to update, implementations should extend this
	}
	
	/**
	 * Creates a MessageTile containing nbt information sent over the network.
	 * This should be called using super and then have nbt data added as required.
	 * @return The MessageTile, complete with nbt data.
	 */
	public MessageTile writeMessageTile()
	{
		return new MessageTile(this);
	}
	
	
	/* NETWORK GUI																	 		*/
	/* Inform the client that a gui has changed while a player is looking at it.			*/
	
	/**
	 * Updates a tile using the data specified.
	 * @param nbt The nbt containing updates.
	 */
	public void readMessageGui(NBTTagCompound nbt) 
	{
		//nothing to update, implementations should extend this
	}
	
	/**
	 * Creates a MessageTile containing nbt information sent over the network.
	 * This should be called using super and then have nbt data added as required.
	 * @return The MessageTile, complete with nbt data.
	 */
	public MessageGui writeMessageGui() 
	{
		return new MessageGui(this);
	}
	
	/**
	 * Checks that a player is in range to access this tile, mostly used by Guis.
	 * @param player The player.
	 * @return true if in range, false otherwise.
	 */
	public boolean isPlayerInRange(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

}
