package com.mabinogi.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class RenderUtil {
	
	private static Minecraft minecraft = Minecraft.getMinecraft();
	
	public static void bindBlockTexture()
	{
		minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	}
	
	public static void bindColor(int color) 
	{
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		GlStateManager.color(red, green, blue, 1.0F);
	}

	public static void resetColor() 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static TextureAtlasSprite getTexture(ResourceLocation location) 
	{
		return getTexture(location.toString());
	}
	
	public static TextureAtlasSprite getTexture(String location) 
	{
		return minecraft.getTextureMapBlocks().getAtlasSprite(location);
	}
	
	public static TextureAtlasSprite getMissingTexture() 
	{
		return minecraft.getTextureMapBlocks().getMissingSprite();
	}
	
	public static TextureAtlasSprite getFluidTextureStill(FluidStack fluidStack) 
	{	
		if (fluidStack == null) return getMissingTexture();
		
		final Fluid fluid = fluidStack.getFluid();
		if (fluid == null) return getMissingTexture();
		
		return getTexture(fluid.getStill());
	}
	
	public static TextureAtlasSprite getFluidTextureFlowing(FluidStack fluidStack) 
	{	
		if (fluidStack == null) return getMissingTexture();
		
		final Fluid fluid = fluidStack.getFluid();
		if (fluid == null) return getMissingTexture();
		
		return getTexture(fluid.getFlowing());
	}

}
