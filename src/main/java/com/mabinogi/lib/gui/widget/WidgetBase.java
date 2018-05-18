package com.mabinogi.lib.gui.widget;

import com.mabinogi.lib.gui.GuiBase;
import com.mabinogi.lib.util.shape.Rectangle4i;

public class WidgetBase {
	
	protected GuiBase gui;
	
	protected int posX = 0;
	protected int posY = 0;
	
	protected int offsetX = 0;
	protected int offsetY = 0;
	
	protected int width;
	protected int height;
	
	public WidgetBase(GuiBase gui, int posX, int posY, int width, int height) 
	{
		this.gui = gui;
		
		this.posX = posX;
		this.posY = posY;
		
		this.width = width;
		this.height = height;
	}

	public void draw(int offsetX, int offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void drawTooltip(int x, int y)
	{
		//do nothing, base only
	}
	
	public boolean isBounds(int x, int y)
	{
		return getBounds().contains(x, y);
	}
	
	public Rectangle4i getBounds() 
	{
		return new Rectangle4i(posX + offsetX, posY + offsetY, width, height);
	}
	
}
