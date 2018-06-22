package com.mabinogi.lib.network.messages;

import java.io.IOException;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class MessageBase implements IMessage {
	
	public MessageBase() { }
    
    /**
     * Reads a compressed NBTTagCompound from this buffer
     */
    @Nullable
    public NBTTagCompound readNBTTagCompoundFromBuffer(ByteBuf buf)
    {
        int i = buf.readerIndex();
        byte b0 = buf.readByte();

        if (b0 == 0)
        {
            return null;
        }
        else
        {
        	buf.readerIndex(i);

            try
            {
                return CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(2097152L));
            }
            catch (IOException ioexception)
            {
                throw new EncoderException(ioexception);
            }
        }
    }
	
	/**
     * Writes a compressed NBTTagCompound to this buffer
     */
    public ByteBuf writeNBTTagCompoundToBuffer(ByteBuf buf, @Nullable NBTTagCompound nbt)
    {
        if (nbt == null)
        {
            buf.writeByte(0);
        }
        else
        {
            try
            {
                CompressedStreamTools.write(nbt, new ByteBufOutputStream(buf));
            }
            catch (IOException ioexception)
            {
                throw new EncoderException(ioexception);
            }
        }
        
        return buf;
    }

}