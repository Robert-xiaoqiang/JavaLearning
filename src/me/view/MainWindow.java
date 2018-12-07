/**
 * 
 */
package me.view;

import javax.swing.*;
import javax.swing.border.Border;

import me.common.notification.IPropertyNotification;
import me.common.statemachine.QState;
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


/**
 * @author Q
 *
 */
public class MainWindow extends JFrame {
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
		createFunctionButton();
		createMainPanel();
		
		initMainPanel();
		initMenuBar();
		initToolBar();
		initFunctionButton();
		
		// JFrame
	    setSize(1280, 640);
	    setLocationRelativeTo(null); // Center the s
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    getContentPane().setBackground(Color.WHITE);
	   
	    setVisible(true);
	}
	
	public void bindStateMachine(QState current)
	{
		this.current = current;
	}
	
	public void bindModel(Pages pages)
	{
		this.pages = pages;
		mainPanel.bindModel(pages);
		// bind sinks and notification
		pages.addPropertyNotification(this.getViewSink());
	}
	
	public void bindRectangleLable(ActionListener aListener)
	{
		rectangleButton.addActionListener(aListener);
	}
	
	public void bindMainPanel(MouseListener mListener)
	{
		mainPanel.addMouseListener(mListener);
	}
	
	// update
	public void update()
	{
		mainPanel.repaint();
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
	
	private JToolBar toolBar;
	private JButton openButton;
	private JButton closeButton;
	private JButton saveButton;
	private JButton saveAsButton;
	
	// panel component
	private QPanel mainPanel;
	
	// function component
	private JButton rectangleButton;
	private JButton circleButton;
	
	// state machine
	private QState current;
	
	// model information
	private Pages pages;
	
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
	
	private void initFunctionButton()
	{
		GridLayout functionLayout = new GridLayout(3, 1);
		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(functionLayout);
		functionPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		functionPanel.add(rectangleButton);
		functionPanel.add(circleButton);
		functionPanel.add(new JButton("Button"));
		add(functionPanel, BorderLayout.EAST);
	}
	
	private void initMainPanel()
	{
		add(mainPanel, BorderLayout.CENTER);
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
		openButton = new JButton("Open");
		closeButton = new JButton("Close");
		saveButton = new JButton("Save");
		saveAsButton = new JButton("Save As");		
	}
	
	private void createFunctionButton()
	{
		rectangleButton = new JButton();
		rectangleButton.setBackground(Color.WHITE);
		ImageIcon rectIcon = new ImageIcon("resource/rectangle.png"); 
		rectangleButton.setIcon(rectIcon);
		
		circleButton = new JButton("Circle");
	}
	
	private void createMainPanel()
	{
		mainPanel = new QPanel();
	}
	
}
