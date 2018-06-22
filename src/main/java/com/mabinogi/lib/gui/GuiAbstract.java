package com.mabinogi.lib.gui;

import org.lwjgl.opengl.GL11;

import com.mabinogi.lib.gui.container.ContainerBase;
import com.mabinogi.lib.util.RenderUtil;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;

public abstract class GuiAbstract extends GuiContainer {

	public GuiAbstract(ContainerBase container)
	{
		super(container);
	}
	
	public FontRenderer getFontRenderer()
	{
		return fontRenderer;
	}
	
	/**
	 * Fills a specified area with a texture tiled with 16x16 icons
	 * Credit and thanks to Team CoFH
	 * @param x The horizontal starting location
	 * @param y The vertical starting location
	 * @param icon The icon to tile
	 * @param width The width to tile
	 * @param height The height to tile
	 */
	public void drawTiledTexture(int x, int y, TextureAtlasSprite icon, int width, int height) 
	{
		int i;
		int j;

		int drawHeight;
		int drawWidth;

		for (i = 0; i < width; i += 16) 
		{
			for (j = 0; j < height; j += 16) 
			{
				drawWidth = Math.min(width - i, 16);
				drawHeight = Math.min(height - j, 16);
				drawScaledTextureIcon(x + i, y + j, icon, drawWidth, drawHeight);
			}
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Draws a icon texture by scaling it to the specified location
	 * Credit and thanks to Team CoFH
	 * @param x The horizontal starting location
	 * @param y The vertical starting location
	 * @param icon The icon to draw
	 * @param width The width to draw
	 * @param height The height to draw
	 */
	public void drawScaledTextureIcon(int x, int y, TextureAtlasSprite icon, int width, int height) 
	{
		if (icon == null) 
		{
			return;
		}
		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();

		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(x, y + height, this.zLevel).tex(minU, minV + (maxV - minV) * height / 16F).endVertex();
		buffer.pos(x + width, y + height, this.zLevel).tex(minU + (maxU - minU) * width / 16F, minV + (maxV - minV) * height / 16F).endVertex();
		buffer.pos(x + width, y, this.zLevel).tex(minU + (maxU - minU) * width / 16F, minV).endVertex();
		buffer.pos(x, y, this.zLevel).tex(minU, minV).endVertex();
		Tessellator.getInstance().draw();
	}
	
	/**
	 * Draws a fluid using its still texture to a specified location
	 * @param x The horizontal starting location
	 * @param y The vertical starting location
	 * @param fluidStack The fluid to draw
	 * @param width The width to draw
	 * @param height The height to draw
	 */
	public void drawFluid(int x, int y, FluidStack fluidStack, int width, int height) 
	{
		if (fluidStack == null || fluidStack.getFluid() == null) 
		{
			return;
		}
		
		GL11.glPushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		//bind block texture
		RenderUtil.bindBlockTexture();
		
		//set color to match fluids
		RenderUtil.bindColor(fluidStack.getFluid().getColor(fluidStack));
		
		//draw the texture as tiles
		drawTiledTexture(x, y, RenderUtil.getFluidTextureStill(fluidStack), width, height);
		GL11.glPopMatrix();
	}

}
