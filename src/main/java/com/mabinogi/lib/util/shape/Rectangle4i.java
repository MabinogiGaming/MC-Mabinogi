package com.mabinogi.lib.util.shape;

public class Rectangle4i
{
	public int x;
	public int y;
  
	public int w;
	public int h;
  
	public Rectangle4i(int x, int y, int w, int h)
	{
	    this.x = x;
	    this.y = y;
	    this.w = w;
	    this.h = h;
	}
  
	public boolean contains(int pointX, int pointY) 
	{
		return (this.x <= pointX) && (pointX < this.x + this.w)	&& (this.y <= pointY) && (pointY < this.y + this.h);
	}

	public boolean intersects(Rectangle4i rectangle) 
	{
		return (rectangle.x + rectangle.w > this.x)	&& (rectangle.x < this.x + this.w) && (rectangle.y + rectangle.h > this.y) && (rectangle.y < this.y + this.h);
	}
}
