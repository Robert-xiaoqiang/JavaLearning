package me.model;

import java.awt.Color;
import java.io.Serializable;

// default friendly privilege
public abstract class AbstractShape implements IShape, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract void translate(int deltaX, int deltaY);
	
	public AbstractShape(double stroke, Color edgeColor, Color fillColor) 
	{
		this.stroke = stroke;
		this.edgeColor = edgeColor;
		this.fillColor = fillColor;
	}

	public void setStroke(double d)
	{
		stroke = d;
	}
	
	public double getStroke()
	{
		return stroke;
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
	
	protected double stroke = DefaultSetting.stroke;
	protected Color edgeColor = DefaultSetting.edgeColor;
	protected Color fillColor = DefaultSetting.fillColor;
	protected boolean isFill = false;
}
