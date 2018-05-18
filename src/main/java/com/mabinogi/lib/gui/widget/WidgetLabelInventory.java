package com.mabinogi.lib.gui.widget;

import com.mabinogi.lib.gui.GuiBase;
import com.mabinogi.lib.gui.component.ComponentLabel;

import net.minecraft.client.resources.I18n;

public class WidgetLabelInventory extends WidgetBase {
	
	private ComponentLabel label;
	
	public WidgetLabelInventory(GuiBase gui)
	{
		super(gui, 8, gui.getYSize() - 94, 0, 0);
		
		label = new ComponentLabel();
	}

	@Override
	public void draw(int offsetX, int offsetY)
	{
		label.draw(gui.getFontRenderer(), posX + offsetX, posY + offsetY, I18n.format("container.inventory", new Object[0]), 4210752);
	}
	
	@Override
	public boolean isBounds(int x, int y)
	{
		return false;
	}

}