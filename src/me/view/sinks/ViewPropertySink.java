package me.view.sinks;

import me.common.notification.IPropertyNotification;
import me.view.MainWindow;

public class ViewPropertySink implements IPropertyNotification {

	private MainWindow window; 
	
	// previous declaration in C++
	// how about Java, no Use new is OK???
	
	public ViewPropertySink(MainWindow window) 
	{
		this.window = window;
	}

	@Override
	public void onPropertyChanged(String info) 
	{
		// discard info
		// repaint all
		window.update();
	}

}
