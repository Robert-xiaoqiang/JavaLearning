package me.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class QRectangle extends AbstractShape {
	public QRectangle(int x, int y, int width, int height, double stroke, Color edgeColor, Color fillColor) 
	{
		super(stroke, edgeColor, fillColor);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Graphics g) 
	{
		g.setColor(edgeColor);
		g.drawRect(x, y, width, height);
		if(isFill) {
			g.setColor(fillColor);
			g.fillRect(x + 1, y + 1, width - 2, height - 2);			
		}

	}

	@Override
	public boolean isInner(Point p) 
	{
		return p.x > x && p.x < (x + width) && p.y > y && p.y < (y + height);
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setWidth(int w)
	{
		width = w;
	}
	
	public void setHeight(int h)
	{
		height = h;
	}
	private int x, y;
	private int width, height;
	
	// default value
	private boolean isFill = false;
}
