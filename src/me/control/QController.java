package me.control;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.common.event.ColorChangedListener;
import me.common.statemachine.QState;
import me.common.statemachine.QStateMachine;
import me.model.AbstractShape;
import me.model.DefaultSetting;
import me.model.Pages;
import me.model.QRectangle;
import me.model.QText;

public class QController {	
	private class RectangleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) 
		{		
			// action
			hits.clear();
			
			// OTHERS <-> QSTATE_RECTANGLE
			current.setCurrent(current.getCurrent().rectangleButton());
			
			// JComonent repaint
			// control <-select-> view
			Pages pages = model.getLast();
			pages.requestPropertyChanged("QRectangle");
		}
		
	}
	private class TextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			hits.clear();
			current.setCurrent(current.getCurrent().textButton());
			
			// JComonent repaint
			// control <-select-> view
			Pages pages = model.getLast();
			pages.requestPropertyChanged("QText");
		}
		
	}
	private class MainPanelListener implements MouseListener, 
											   MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e)
		{
			int b = e.getButton();
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// action
			// override equals() is not necessary
			if(cur == QState.QSTATE_IDLE) {
				if(b == MouseEvent.BUTTON1) {
					// clear all
					hits.clear();
					// add
					java.util.List<Integer> la = pages.getHits(new Point(e.getX(), e.getY()));
					hits.addAll(la);
				}
			} else if(cur == QState.QSTATE_RECTANGLE) {
				if(b == MouseEvent.BUTTON1) {
					pages.add(new QRectangle(e.getX(), e.getY(), 
							 0, 0, stroke, size,
							 edgeColor, fillColor,
							 isFill));					
				}
			} else if(cur == QState.QSTATE_TEXT) {
				if(b == MouseEvent.BUTTON1) {
					String text = JOptionPane.showInputDialog("What about this?");
					if(text != null && !text.isEmpty())
						pages.add(new QText(e.getX(), e.getY(), text, b, size, edgeColor, fillColor));
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
			// meaningless
			int b = e.getButton();
			Pages pages = model.getLast();
			// action
			QState cur = current.getCurrent();
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				int deltaX = e.getX() - mousePoint.x;
				int deltaY = e.getY() - mousePoint.y;
				pages.translate(hits.get(0), deltaX, deltaY);				
			}
			mousePoint.x = e.getX();
			mousePoint.y = e.getY();

		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{			
			// action
			Pages pages = model.getLast();
			QState cur = current.getCurrent();
			mousePoint.x = e.getX();
			mousePoint.y = e.getY();
			if(cur == QState.QSTATE_RENDER_RECTANGLE) {
				// make sure down-cast safe
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
				// create no new Object
				pages.updateNow(top);
			}
			
			// state transition
		}
	}
	
	private class EdgeColorChangedListener implements ColorChangedListener {
		@Override
		public void onColorChanged(Color c)
		{
			edgeColor = c;
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// update hits shape attributes
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				pages.updateEdgeColor(hits.get(0), edgeColor);
			}
		}
	}
	
	private class FillColorChangedListener implements ColorChangedListener {
		@Override
		public void onColorChanged(Color c)
		{
			fillColor = c;
			
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// update hits shape attributes
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				pages.updateFillColor(hits.get(0), fillColor);
			}
		}
	}
	
	private class EdgeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("EDGE")) {
				isFill = false;
			}
			
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// update hits shape attributes
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				pages.updateIsFill(hits.get(0), isFill);
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
			
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// update hits shape attributes
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				pages.updateIsFill(hits.get(0), isFill);
			}
		}
	}
	
	private class StrokeSliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent c)
		{
			JSlider source = (JSlider)c.getSource();
			//if(!source.getValueIsAdjusting()) {
				stroke = (double)source.getValue();
			//}
			
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// update hits shape attributes
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				pages.updateStroke(hits.get(0), stroke);
			}
		}
	}
	
	private class FontSizeSliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent c)
		{
			JSlider source = (JSlider)c.getSource();
			// set font size
			size = source.getValue();
			
			QState cur = current.getCurrent();
			Pages pages = model.getLast();
			// update hits shape attributes
			if(cur == QState.QSTATE_IDLE && !hits.isEmpty()) {
				// font size
				// shape size also
				pages.updateSize(hits.get(0), size);
			}
		}
	}
	
	private class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(!hits.isEmpty()) {
				Pages pages = model.getLast();
				pages.remove(hits.get(0));
				hits.remove(0);
			}
		}
	}
	
	
	public QController() 
	{
		current = null;
		model = null;
		edgeColor = DefaultSetting.edgeColor;
		fillColor = DefaultSetting.fillColor;	
		
		size = DefaultSetting.fontSize;
		
		// buffer
		hits = new LinkedList<>();
		mousePoint = new Point(0, 0);
		
		// file operations is in View
	}
	
	public void bindModel(LinkedList<Pages> model)
	{
		this.model = model;
	}

	public void bindStateMachine(QStateMachine current)
	{
		this.current = current;
	}
	
	public ActionListener getRectangleButtonListener()
	{
		return new RectangleButtonListener();
	}
	
	public ActionListener getTextButtonListener()
	{
		return new TextButtonListener();
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
	
	public ChangeListener getFontSizeSliderListener()
	{
		return new FontSizeSliderListener();
	}
	
	public ActionListener getDeleteListener()
	{
		return new DeleteListener();
	}
	
	// automata
	private QStateMachine current = null;
	// model
	private LinkedList<Pages> model; 
	private Color edgeColor = null;
	private Color fillColor = null;
	
	private int size;
	private double stroke = 0.0;
	private boolean isFill = false;
	
	// buffer for hitting
	// private java.util.List<AbstractShape> hits;
	private java.util.List<Integer> hits;
	private Point mousePoint; // just one point
}
