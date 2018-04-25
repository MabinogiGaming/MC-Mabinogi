package com.mabinogi.lib.block;

import org.lwjgl.input.Keyboard;

import com.mabinogi.lib.block.iface.IFacing;
import com.mabinogi.lib.util.CollectionUtil;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Basic block with no tile associated, also handles IFacing blocks
 * @author Mabinogi
 */
public abstract class BlockBase extends BlockAbstract {
	
	public BlockBase(Material material)
	{
		super(material);
		
		setDefaultState(getInitialState());
	}
	
	/**
	 * Creates the block state, assigning properties to it 
	 */
	@Override
	protected BlockStateContainer createBlockState()
    {
		if (this instanceof IFacing)
		{
			return new BlockStateContainer(this, IFacing.FACING);
		}
		return super.createBlockState();
    }
	
	/**
	 * Initialises the block state, assigning default values to the properties
	 * @return The block state, with properties initialised
	 */
	public IBlockState getInitialState()
	{
		if (this instanceof IFacing)
		{
			return blockState.getBaseState().withProperty(IFacing.FACING, EnumFacing.NORTH);
		}
		return blockState.getBaseState();
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public IBlockState getStateFromMeta(int meta) 
	{
		//if a facing block, get facing from metadata
		if (this instanceof IFacing)
		{
			return getDefaultState().withProperty(IFacing.FACING, EnumFacing.getFront(meta & 7));
		}
		return super.getStateFromMeta(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) 
    {
    	//if a facing block, save facing state
    	if (this instanceof IFacing)
		{
    		return state.getValue(IFacing.FACING).getIndex();
		}
    	return super.getMetaFromState(state);
    }
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) 
    {
    	//if a facing block, rotate it to face the player
    	if (this instanceof IFacing)
		{
    		((IFacing) this).setFacing(world, pos, state, ((IFacing) this).getFacingFromEntity(pos, entity));
		}
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if (world.isRemote)
        {
            return true;
        }
    	
    	//holding shift will rotate the block
    	if (this instanceof IFacing && (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)))
    	{
    		rotateBlock(world, pos, facing);
    		return true;
    	}
    	
    	//holding ctrl will set the block facing
    	if (this instanceof IFacing && (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)))
    	{
    		((IFacing) this).setFacing(world, pos, state, facing);
    		return true;
    	}
    	
    	return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
    
    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
    	if (this instanceof IFacing)
    	{
    		//if a facing block, rotate it to the next valid state
    		IBlockState newState;
    		IBlockState currentState = world.getBlockState(pos);
    		EnumFacing facing = currentState.getValue(IFacing.FACING);
    		
    		newState = currentState.withProperty(IFacing.FACING, CollectionUtil.cycleCollection(((IFacing) this).getValidFacings(), facing));
    		
    		world.setBlockState(pos, newState);
            return true;
    	}
    	return super.rotateBlock(world, pos, axis);
    }

}
