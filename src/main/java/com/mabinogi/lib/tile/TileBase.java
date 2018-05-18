package com.mabinogi.lib.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {
	
	public TileBase()
    {
		super();
    }
	
	/**
	 * Checks that a player is in range to access this tile, mostly used by Guis
	 * @param player The player
	 * @return true if in range, false otherwise
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
