package me.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.common.statemachine.QState;
import me.model.Pages;
import me.model.QRectangle;
import me.view.*;

public class QController {	
	private class RectangleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// int b = e.getButton();
			
			// whatever BUTTON1 or BUTTON3
			// OTHERS <-> QSTATE_RECTANGLE
			current = current.rectangleButton();
			
		}
		
	}
	
	private class MainPanelListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event)
		{
			// override equals() is not necessary
			if(current == QState.QSTATE_RECTANGLE) {
				pages.add(new QRectangle(100, 100, 100, 200, 2.0, Color.blue, Color.blue));
			}
		}
		
		@Override
		public void mousePressed(MouseEvent event)
		{
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			
		}
	}
	
	public QController() 
	{
		current = null;
		pages = null;
	}
	
	public void bindModel(Pages pages)
	{
		this.pages = pages;
	}

	public void bindStateMachine(QState current)
	{
		this.current = current;
	}
	
	public ActionListener getRectangleButtonListener()
	{
		return new RectangleButtonListener();
	}
	
	public MouseListener getMainPanelListener()
	{
		return new MainPanelListener();
	}
	
	private QState current = null;
	private Pages pages = null;
}
