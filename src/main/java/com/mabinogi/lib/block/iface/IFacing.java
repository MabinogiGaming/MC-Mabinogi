package com.mabinogi.lib.block.iface;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is an interface used to specify that a block has facing information included with it
 * It is implemented in the BlockBase class
 * @author Mabinogi
 */
public interface IFacing {
    
    //helper facing values
    public static final List<EnumFacing> FACING_ALL = Arrays.asList(EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST);
    public static final List<EnumFacing> FACING_HORIZONTAL = Arrays.asList(EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST);
	
	//the blocks facing property
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	/**
	 * Get the facing direction of a block
	 * @param state The block state
	 * @return The current EnumFacing of the block
	 */
	public static EnumFacing getFacing(IBlockState state)
	{
		return state.getValue(FACING);
	}
	
	/**
	 * Set the facing direction of a block, cycling it if not valid
	 * @param world The world
	 * @param pos The block position
	 * @param state The block state
	 * @param facing The facing to set the block
	 */
	default public void setFacing(World world, BlockPos pos, IBlockState state, EnumFacing facing)
	{
		if (getValidFacings().contains(facing))
    	{
			world.setBlockState(pos, state.withProperty(FACING, facing), 2);
    	}
	}

	/**
	 * Calculates the facing of a block relative to the player, so that the block will face the player
	 * If that facing is not allowed due to allowYAxis() it is replaced by NORTH
	 * @param pos The position of the block
	 * @param entity The player that placed the block
	 * @return The EnumFacing that best faces the player
	 */
    default public EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase entity) 
    {
    	EnumFacing facing = EnumFacing.getFacingFromVector((float) (entity.posX - pos.getX()), (float) (entity.posY - pos.getY()), (float) (entity.posZ - pos.getZ()));
    	
    	if (!getValidFacings().contains(facing))
    	{
    		facing = getValidFacings().get(0);
    	}
    	
    	return facing;
    }
    
    /**
     * A list of valid facings for rotation, the list must contain at least one item
     * @return A list of EnumFacing containing all valid facing
     */
    default public List<EnumFacing> getValidFacings()
    {
    	return FACING_ALL;
    }

}
