package com.mabinogi.lib.gui.widget;

import com.mabinogi.lib.gui.GuiBase;
import com.mabinogi.lib.tile.TileInventory;
import com.mabinogi.lib.util.RenderUtil;

import net.minecraftforge.fluids.FluidTank;

public class WidgetFluidTank extends WidgetBase {
	
	public static final int WIDTH = 22;
	public static final int HEIGHT = 62;
	
	private int tankID = -1;

	public WidgetFluidTank(GuiBase gui, int posX, int posY, int tankID)
	{
		super(gui, posX, posY, WIDTH, HEIGHT);
		
		this.tankID = tankID;
	}
	
	public void draw(int offsetX, int offsetY)
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
        
        if (gui.tile instanceof TileInventory)
        {
        	//reset color
        	RenderUtil.resetColor();
        	
        	//draw tank
        	gui.mc.getTextureManager().bindTexture(gui.getWidgetTexture());
        	gui.drawTexturedModalRect(posX + offsetX, posY + offsetY, 0, 0, width, height);
        	
        	//draw fluid
        	FluidTank tank = ((TileInventory) gui.tile).getTank(tankID);
        	if (tank != null)
        	{
        		int amount = (int) ((long) tank.getFluidAmount() * (height - 2) / tank.getCapacity());        		
        		gui.drawFluid(posX + 1 + offsetX, posY + 1 + offsetY + (height - 2) - amount, tank.getFluid(), (width - 2), amount);
        	}
        }
	}
	
	public void drawTooltip(int x, int y)
	{
		//do nothing, base only
	}

}
