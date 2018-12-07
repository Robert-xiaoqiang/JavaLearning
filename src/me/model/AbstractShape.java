package me.model;

import java.awt.Color;

// default friendly privilege
abstract class AbstractShape implements IShape {
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
		edgeColor = c;
	}

	
	protected double stroke = DefaultSetting.stroke;
	protected Color edgeColor = DefaultSetting.edgeColor;
	protected Color fillColor = DefaultSetting.fillColor;
}
