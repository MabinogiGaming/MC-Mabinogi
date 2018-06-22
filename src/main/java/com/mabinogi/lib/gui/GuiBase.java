package com.mabinogi.lib.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.mabinogi.lib.MabinogiSettings;
import com.mabinogi.lib.gui.container.ContainerBase;
import com.mabinogi.lib.gui.widget.WidgetBase;
import com.mabinogi.lib.tile.iface.IGuiTile;

import net.minecraft.util.ResourceLocation;

public abstract class GuiBase extends GuiAbstract {
	
	private final ResourceLocation widgetTexture = new ResourceLocation(MabinogiSettings.MODID + ":textures/gui/widgets.png");

	public ArrayList<WidgetBase> widgets = new ArrayList<>();
	
	public IGuiTile tile;
	
	public GuiBase(ContainerBase container, IGuiTile tile) 
	{
		super(container);
		
		this.tile = tile;
		
		xSize = container.getGuiWidth();
		ySize = container.getGuiHeight();
	}
	
	public ResourceLocation getWidgetTexture()
	{
		return widgetTexture;
	}
	
	public abstract ResourceLocation getGuiTexture();
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
	}

    /**
     * Draw the background layer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float ticks, int mouseX, int mouseY)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        mc.getTextureManager().bindTexture(getGuiTexture());
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        for (WidgetBase widget : widgets)
        {
    		widget.draw(guiLeft, guiTop);
        }
    }
	
	/**
     * Draw the foreground layer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	WidgetBase mouseOver = getWidgetForPosition(mouseX, mouseY);
    	if (mouseOver != null)
    	{
    		mouseOver.drawTooltip(mouseX - guiLeft, mouseY - guiTop);
    	}
    }
	
	public WidgetBase getWidgetForPosition(int x, int y)
	{
		for (WidgetBase widget : widgets)
		{
			if (widget.isBounds(x, y))
			{
				return widget;
			}
		}
		return null;
	}

}
