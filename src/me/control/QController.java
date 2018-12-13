package me.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.common.event.ColorChangedListener;
import me.common.statemachine.QState;
import me.common.statemachine.QStateMachine;
import me.model.DefaultSetting;
import me.model.Pages;
import me.model.QRectangle;
import me.view.*;

public class QController {	
	private class RectangleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) 
		{		
			// OTHERS <-> QSTATE_RECTANGLE
			current.setCurrent(current.getCurrent().rectangleButton());
			
			// JComonent repaint
			// control <-select-> view
			pages.requestPropertyChanged("QRectangle");
		}
		
	}
	
	private class MainPanelListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e)
		{
			int b = e.getButton();
			// action
			// override equals() is not necessary
			if(b == MouseEvent.BUTTON1) {
				if(current.getCurrent() == QState.QSTATE_RECTANGLE) {
					pages.add(new QRectangle(e.getX(), e.getY(), 
											 0, 0, stroke,
											 edgeColor, fillColor,
											 isFill));
				}				
			}
			
			// state transition
			if(b == MouseEvent.BUTTON1) {
				current.setCurrent(current.getCurrent().leftButton());
			} else if(b == MouseEvent.BUTTON3) {
				current.setCurrent(current.getCurrent().rightButton());
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

		@Override
		public void mouseDragged(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{			
			// action
			if(current.getCurrent() == QState.QSTATE_RENDER_RECTANGLE) {
				// make sure up-cast safe
				QRectangle top = (QRectangle)pages.now();
				int newX = e.getX(), newY = e.getY();
				int oldX = top.getp1X(), oldY = top.getp1Y();
				int minX = Math.min(newX, oldX);
				int minY = Math.min(newY, oldY);
				int newWidth = Math.abs(newX - oldX);
				int newHeight = Math.abs(newY - oldY);
				top.setX(minX); top.setY(minY);
				top.setWidth(newWidth); top.setHeight(newHeight);
				// notify
				// not pages
				// but elements & attributes directly here
				pages.requestPropertyChanged("QRectangle");
			}
			// state transition
		}
	}
	
	private class EdgeColorChangedListener implements ColorChangedListener {
		@Override
		public void onColorChanged(Color c)
		{
			edgeColor = c;
		}
	}
	
	private class FillColorChangedListener implements ColorChangedListener {
		@Override
		public void onColorChanged(Color c)
		{
			fillColor = c;
		}
	}
	
	private class EdgeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("EDGE")) {
				isFill = false;
			}
		}
	}
	
	private class FillButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// dependent of construction
			// hahahahahahahahahahahahaha
			if(e.getActionCommand().equals("FILL")) {
				isFill = true;
			}
		}
	}
	
	private class StrokeSliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent c)
		{
			JSlider source = (JSlider)c.getSource();
			if(!source.getValueIsAdjusting()) {
				stroke = (double)source.getValue();
			}
		}
	}
	
	public QController() 
	{
		current = null;
		pages = null;
		edgeColor = DefaultSetting.edgeColor;
		fillColor = DefaultSetting.fillColor;	
	}
	
	public void bindModel(Pages pages)
	{
		this.pages = pages;
	}

	public void bindStateMachine(QStateMachine current)
	{
		this.current = current;
	}
	
	public ActionListener getRectangleButtonListener()
	{
		return new RectangleButtonListener();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends MouseListener & MouseMotionListener> T getMainPanelListener()
	{
		T ret;
		return ret = (T)new MainPanelListener(); 
	}
	
	public ColorChangedListener getEdgeColorChangedListener()
	{
		return new EdgeColorChangedListener();
	}
	
	public ColorChangedListener getFillColorChangedListener()
	{
		return new FillColorChangedListener();
	}
	
	public ActionListener getEdgeButtonListener()
	{
		return new EdgeButtonListener();
	}
	
	public ActionListener getFillButtonListener()
	{
		return new FillButtonListener();
	}
	
	public ChangeListener getStrokeSliderListener()
	{
		return new StrokeSliderListener();
	}
	
	private QStateMachine current = null;
	private Pages pages = null;
	private Color edgeColor = null;
	private Color fillColor = null;
	private double stroke = 0.0;
	private boolean isFill = false;
}
