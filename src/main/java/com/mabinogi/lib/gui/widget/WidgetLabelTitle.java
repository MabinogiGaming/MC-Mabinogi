package com.mabinogi.lib.gui.widget;

import com.mabinogi.lib.gui.GuiBase;
import com.mabinogi.lib.gui.component.ComponentLabel;

public class WidgetLabelTitle extends WidgetBase {
	
	private ComponentLabel label;
	
	public WidgetLabelTitle(GuiBase gui)
	{
		super(gui, 8, 6, 0, 0);
		
		label = new ComponentLabel();
	}

	@Override
	public void draw(int offsetX, int offsetY)
	{
		label.draw(gui.getFontRenderer(), posX + offsetX, posY + offsetY, gui.tile.getInventoryName(), 4210752);
	}
	
	@Override
	public boolean isBounds(int x, int y)
	{
		return false;
	}

}
