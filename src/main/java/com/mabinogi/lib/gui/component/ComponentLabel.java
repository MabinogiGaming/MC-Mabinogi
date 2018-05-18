package com.mabinogi.lib.gui.component;

import net.minecraft.client.gui.FontRenderer;

public class ComponentLabel {
	
	public void draw(FontRenderer fontRenderer, int x, int y, String str, int color)
	{
		fontRenderer.drawString(str, x, y, color);
	}

}
