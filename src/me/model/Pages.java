package me.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import me.common.notification.ProxyPropertyNotification;

public class Pages extends ProxyPropertyNotification implements Serializable {

	public Pages() 
	{
		shapes = new ArrayList<>();
	}
	
	public void increseStroke(Point p)
	{
		for(AbstractShape i : shapes) {
			if(i.isInner(p)) {
				i.setStroke(i.getStroke() + 1.0);
			}
		}
		// notify view level
		FireOnPropertyChanged("increaseStroke");
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
		FireOnPropertyChanged("decreaseStroke");
	}
	
	public void add(AbstractShape shape)
	{
		shapes.add(shape);
		// notify view level
		FireOnPropertyChanged("add");
	}
	
	public void render(Graphics g)
	{
		System.out.println("here");
		// how about capturing
		shapes.forEach((AbstractShape v) -> {
			v.render(g);
		});
	}
	
	public IShape now()
	{
		return shapes.get(shapes.size() - 1);
	}
	
	private ArrayList<AbstractShape> shapes;
}
