package me.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

// default friendly privilege
public abstract class AbstractShape implements IShape, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract void translate(int deltaX, int deltaY);
	
	public AbstractShape(double stroke, int size, Color edgeColor, Color fillColor) 
	{
		this.stroke = stroke;
		this.edgeColor = edgeColor;
		this.fillColor = fillColor;
		this.size = size;
	}

	public void setStroke(double d)
	{
		stroke = d;
	}
	
	public double getStroke()
	{
		return stroke;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setEdgeColor(Color c)
	{
		edgeColor = c;
	}
	
	public void setFillColor(Color c)
	{
		fillColor = c;
	}

	public void setIsFill(boolean isFill)
	{
		this.isFill = isFill;
	}
	
	@Override
	public void render(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke((float)stroke, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
	}
	
	protected double stroke = DefaultSetting.stroke;
	protected Color edgeColor = DefaultSetting.edgeColor;
	protected Color fillColor = DefaultSetting.fillColor;
	/**
	 * for font => size
	 * for others => ratio amplify/diminish
	 */
	protected int size = DefaultSetting.fontSize;
	protected boolean isFill = false;
}
