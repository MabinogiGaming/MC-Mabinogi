package com.mabinogi.lib.network.messages;

import com.mabinogi.lib.tile.TileBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageGui extends MessageBase implements IMessage, IMessageHandler<MessageGui, IMessage> {
	
	public BlockPos blockPos;
	public NBTTagCompound nbt;
	
	public MessageGui() { }
	
	public MessageGui(TileEntity tile) 
	{
		blockPos = tile.getPos();
		nbt = new NBTTagCompound();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
        buf.writeLong(blockPos.toLong());
        writeNBTTagCompoundToBuffer(buf, this.nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.blockPos = BlockPos.fromLong(buf.readLong());
        this.nbt = readNBTTagCompoundFromBuffer(buf);
	}
	
	@Override
	public IMessage onMessage(MessageGui message, MessageContext ctx) 
	{
		//get the world
		World world;
		if (ctx.side == Side.CLIENT) world = FMLClientHandler.instance().getClient().world;
		else world = ctx.getServerHandler().player.world;
		
		//check world is valid
		if (world == null) return null;
		
		//check it is loaded
		if (!world.isBlockLoaded(message.blockPos)) return null;
				
		//get the tile entity
		TileEntity tileEntity = world.getTileEntity(message.blockPos);
		
		//check the tile entity is valid
		if (tileEntity == null) return null;
		
		//apply the message to the tile entity
		if (tileEntity instanceof TileBase)
		{
			((TileBase) tileEntity).readMessageGui(message.nbt);
			
			//update the block state
			IBlockState state = tileEntity.getWorld().getBlockState(message.blockPos);
			tileEntity.getWorld().notifyBlockUpdate(message.blockPos, state, state, 3);
			tileEntity.getWorld().checkLight(message.blockPos);
		}
		
		return null;
	}

}
