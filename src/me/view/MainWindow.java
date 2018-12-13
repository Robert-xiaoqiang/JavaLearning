/**
 * 
 */
package me.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;

import me.common.event.ColorChangedListener;
import me.common.notification.IPropertyNotification;
import me.common.statemachine.QState;
import me.common.statemachine.QStateMachine;
import me.model.DefaultSetting;
import me.model.Pages;
// here is a nested import
import me.view.sinks.ViewPropertySink;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.OpenType;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * @author Q
 *
 */
public class MainWindow extends JFrame {
	
	private final static long serialVersionUID = 11l;
	public MainWindow(String title) 
	{
		super(title);
		// sink & notify
		// deferred
		
		// view
		setLayout(new BorderLayout());
		// this
		createMenuItem();
		createButton();
		createPanel();
		
		initPanel();
		initMenuBar();
		initToolBar();
		
		// JFrame
	    setSize(1280, 640);
	    setLocationRelativeTo(null); // Center the s
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    getContentPane().setBackground(Color.WHITE);
	   
	    setVisible(true);
	}
	
	public void bindStateMachine(QStateMachine current)
	{
		// this
		this.current = current;
		// function panel
		// select view according to state
		functionPanel.bindStateMachine(current);
	}
	
	public void bindModel(Pages pages)
	{
		this.pages = pages;
		mainPanel.bindModel(this.pages);
		
		// bind sinks and notification
		pages.addPropertyNotification(this.getViewSink());
	}
	
	public void bindRectangleButton(ActionListener aListener)
	{
		functionPanel.bindRectangleButton(aListener);
	}
	
	public <E extends MouseMotionListener & MouseListener> void bindMainPanel(E mListener)
	{
		mainPanel.addMouseListener(mListener);
		mainPanel.addMouseMotionListener(mListener);
	}
	// bind color panel
	public void bindEdgeColorPanel(ColorChangedListener e)
	{
		functionPanel.bindEdgeColorPanel(e);
	}
	// bind color panel
	public void bindFillColorPanel(ColorChangedListener f)
	{
		functionPanel.bindFillColorPanel(f);
	}
	// bind edge button
	public void bindEdgeButton(ActionListener a)
	{
		functionPanel.bindEdgeButton(a);
	}
	// bind fill button from controller
	public void bindFillButton(ActionListener a) 
	{
		functionPanel.bindFillButton(a);
	}
	// bind stroke slider
	public void bindStrokeSlider(ChangeListener c)
	{
		functionPanel.bindStrokeSlider(c);
	}
	// update
	public void update()
	{
		mainPanel.repaint();
		functionPanel.repaint();
	}
	
	// bind sinks
	public IPropertyNotification getViewSink()
	{
		if(viewSink == null) viewSink = new ViewPropertySink(this);
		return viewSink;
	}
	
	// menu component
	private JMenuItem openMenuItem;
	private JMenuItem closeMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu optionsMenu;
	private JMenu helpMenu;
	// tool bar component
	private JToolBar toolBar;
	private JButton openButton;
	private JButton closeButton;
	private JButton saveButton;
	private JButton saveAsButton;
	
	// state machine
	private QStateMachine current;
	
	// model information
	private Pages pages;
	
	// panel component
	private QMainPanel mainPanel;

	// function panel component
	private QFunctionPanel functionPanel;
	
	// sinks
	private ViewPropertySink viewSink = null;
	
	/**
	 * 
	 */
	private void initMenuBar()
	{
		fileMenu = new JMenu("File"); // no tear
		fileMenu.add(openMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		
		editMenu = new JMenu("Edit"); // how about torn off
		optionsMenu = new JMenu("Menu");
		helpMenu = new JMenu("help");
		
		// menu bar
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		//menuBar.add(editMenu);
		
		// this
		setJMenuBar(menuBar);
	}
	
	private void initToolBar()
	{
		// tool bar
		toolBar = new JToolBar();
		toolBar.add(openButton);
		toolBar.add(closeButton);
		toolBar.add(saveButton);
		toolBar.add(saveAsButton);
		
		// this
		add(toolBar, BorderLayout.NORTH);
	}
		
	private void initPanel()
	{
		add(mainPanel, BorderLayout.CENTER);
		add(functionPanel, BorderLayout.EAST);
	}
	
	private void createMenuItem()
	{
		// file
		openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
		closeMenuItem = new JMenuItem("Close", KeyEvent.VK_C);
		saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
		saveAsMenuItem = new JMenuItem("Save As", KeyEvent.VK_V);
		// edit
		// options
		// help
	}
	
	private void createButton()
	{
		// Menu Item
		openButton = new JButton("Open");
		closeButton = new JButton("Close");
		saveButton = new JButton("Save");
		saveAsButton = new JButton("Save As");
	}
		
	private void createPanel()
	{
		mainPanel = new QMainPanel();
		functionPanel = new QFunctionPanel();
	}
	
}
