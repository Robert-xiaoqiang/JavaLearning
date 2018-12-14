package me.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;

public class QRectangle extends AbstractShape {
	public QRectangle(int x, int y, int width, int height, double stroke, Color edgeColor, Color fillColor, boolean isFill) 
	{
		super(stroke, edgeColor, fillColor);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isFill = isFill;
		this.p1 = new Point(x, y);
	}

	@Override
	public void render(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke((float)stroke, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
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
		return p.x >= x && p.x <= (x + width) && p.y >= y && p.y <= (y + height);
	}
	
	// for AbstractShape class
	@Override
	public void translate(int deltaX, int deltaY)
	{
		x += deltaX;
		y += deltaY;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getp1X()
	{
		return p1.x;
	}
	
	public int getp1Y()
	{
		return p1.y;
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
	
	public void setIsFill(boolean isFill)
	{
		this.isFill = isFill;
	}
	private int x, y;
	private int width, height;
	private Point p1 = null;
	// p1, p2 is dummy, just for caching
	// default value
}
