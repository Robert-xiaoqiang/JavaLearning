package me.model;

import java.awt.*;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import me.common.notification.ProxyPropertyNotification;

public class Pages extends ProxyPropertyNotification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pages() 
	{
		shapes = new ArrayList<>();
		isModified = false;
	}
	
	public void increseStroke(Point p)
	{
		for(AbstractShape i : shapes) {
			if(i.isInner(p)) {
				i.setStroke(i.getStroke() + 1.0);
			}
		}
		// notify view level
		fireOnPropertyChanged("increaseStroke");
		isModified = true;
	}
	
	public void decreaseStroke(Point p)
	{
		for(AbstractShape i : shapes) {
			if(i.isInner(p)) {
				double d = i.getStroke();
				i.setStroke(Math.abs(d - 1.0) < 1e-3 ? 
									 DefaultSetting.stroke : 
									 d - 1.0);
			}
		}		
		// notify view level
		fireOnPropertyChanged("decreaseStroke");
		isModified = true;
	}
	
	public void updateStroke(int index, double stroke)
	{
		shapes.get(index).setStroke(stroke);
		fireOnPropertyChanged("stroke");
		isModified = true;
	}
	
	public void updateEdgeColor(int index, Color c)
	{
		shapes.get(index).setEdgeColor(c);
		fireOnPropertyChanged("edge color");
		isModified = true;
	}
	
	public void updateFillColor(int index, Color c)
	{
		shapes.get(index).setFillColor(c);
		fireOnPropertyChanged("fill color");
		isModified = true;
	}
	
	public void updateIsFill(int index, boolean isFill)
	{
		shapes.get(index).setIsFill(isFill);
		fireOnPropertyChanged("is fill");
		isModified = true;
	}
	
	public void translate(int index, int deltaX, int deltaY)
	{
		shapes.get(index).translate(deltaX, deltaY);
		fireOnPropertyChanged("translate");
		isModified = true;
	}
	
	public void add(AbstractShape shape)
	{
		shapes.add(shape);
		// notify view level
		fireOnPropertyChanged("add");
		isModified = true;
	}
	
	public void remove(int id)
	{
		shapes.remove(id);
		// notify
		fireOnPropertyChanged("remove");
		isModified = true;
	}
	
	public void render(Graphics g)
	{
		// how about capturing
		shapes.forEach((AbstractShape v) -> 
		{
			v.render(g);
		});
	}
	
	public java.util.List<Integer> getHits(Point p)
	{	
		return IntStream.range(0, shapes.size())
						.filter(i -> shapes.get(i).isInner(p))
						.boxed()
						.collect(Collectors.toList());				
	}
	
	// dynamic dispatch by state machine
	public IShape now()
	{
		return shapes.get(shapes.size() - 1);
	}
	
	public void updateNow(AbstractShape shape)
	{
		shapes.set(shapes.size() - 1, shape);
		// notify
		fireOnPropertyChanged("top");
		isModified = true;
	}
	
	// here protected -> public
	// intention : elements fire on notification
	public void requestPropertyChanged(String info)
	{
		fireOnPropertyChanged(info);
		isModified = true;
	}
	
	public boolean modelIsModified()
	{
		return isModified;
	}
	
	public void setIsModified(boolean isModified)
	{
		this.isModified = isModified;
	}
	
	// to-be saved
	private ArrayList<AbstractShape> shapes;
	// not-be saved
	private transient boolean isModified = false; 
}
