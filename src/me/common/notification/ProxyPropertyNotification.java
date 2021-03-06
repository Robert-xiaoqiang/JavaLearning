package me.common.notification;

import java.util.LinkedList;

public class ProxyPropertyNotification {
	public ProxyPropertyNotification() 
	{
		sinks = new LinkedList<>();
	}
	
	public void addPropertyNotification(IPropertyNotification p)
	{
		sinks.add(p);
	}
	
	protected void fireOnPropertyChanged(String info)
	{
		sinks.forEach((e) -> 
		{
			e.onPropertyChanged(info);
		});
	}
	
	private LinkedList<IPropertyNotification> sinks;
}
