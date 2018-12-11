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
		createFunctionArea();
		createPanel();
		
		initPanel();
		initMenuBar();
		initToolBar();
		initFunctionArea();
		
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
	
	public void bindRectangleButton(ActionListener aListener)
	{
		rectangleButton.addActionListener(aListener);
	}
	
	public <E extends MouseMotionListener & MouseListener> void bindMainPanel(E mListener)
	{
		mainPanel.addMouseListener(mListener);
		mainPanel.addMouseMotionListener(mListener);
	}
	// bind color panel
	public void bindEdgeColorPanel(ColorChangedListener e)
	{
		edgeFillColorPanel.addEdgeColorChangeListener(e);
	}
	// bind color panel
	public void bindFillColorPanel(ColorChangedListener f)
	{
		edgeFillColorPanel.addFillColorChangedListener(f);
	}
	// bind edge button
	public void bindEdgeButton(ActionListener a)
	{
		edgeButton.addActionListener(a);
	}
	// bind fill button from controller
	public void bindFillButton(ActionListener a) 
	{
		fillButton.addActionListener(a);
	}
	// bind stroke slider
	public void bindStrokeSlider(ChangeListener c)
	{
		strokeSlider.addChangeListener(c);
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
	// tool bar component
	private JToolBar toolBar;
	private JButton openButton;
	private JButton closeButton;
	private JButton saveButton;
	private JButton saveAsButton;
	
	// selection button
	private JRadioButton edgeButton;
	private JRadioButton fillButton;
	private ButtonGroup edgeFillGroup; // just new & add is OK
	
	// slider
	private JSlider strokeSlider; 
	
	// panel component
	private QPanel mainPanel;
	
	// edge color + fill color
	private ColorPanel edgeFillColorPanel;
	
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
	
	private void initFunctionArea()
	{
		GridLayout functionLayout = new GridLayout(5, 1);
		JPanel functionPanel = new JPanel();
		// button
		functionPanel.setLayout(functionLayout);
		functionPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		functionPanel.add(rectangleButton);
		functionPanel.add(circleButton);
		functionPanel.add(new JButton("Button"));
		
		// color panel
		functionPanel.add(edgeFillColorPanel);
		
		// edge/fill button
		JPanel edgeFillPanel = new JPanel();
		edgeFillPanel.setLayout(new GridLayout(1, 2));
		edgeFillPanel.setBorder(new LineBorder(Color.ORANGE, 5));
		edgeFillPanel.add(edgeButton);
		edgeFillPanel.add(fillButton);
		
		// edge/fill + stroke slider => panel
		JPanel leftRightPanel = new JPanel(new GridLayout(1, 2));
		leftRightPanel.add(strokeSlider);
		leftRightPanel.add(edgeFillPanel);
		
		// function panel
		functionPanel.add(leftRightPanel);
		
		// this
		add(functionPanel, BorderLayout.EAST);
	}
		
	private void initPanel()
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
		// Menu Item
		openButton = new JButton("Open");
		closeButton = new JButton("Close");
		saveButton = new JButton("Save");
		saveAsButton = new JButton("Save As");
	}
	
	private void createFunctionArea()
	{
		// button function
		rectangleButton = new JButton();
		rectangleButton.setBackground(Color.WHITE);
		ImageIcon rectIcon = new ImageIcon("resource/rectangle.png"); 
		rectangleButton.setIcon(rectIcon);
		// button function
		circleButton = new JButton("Circle");
		
		edgeFillColorPanel = new ColorPanel();
		// edge fill button
		edgeButton = new JRadioButton("Not Fill", true);
		// edgeButton.setMnemonic(KeyEvent.VK_E);
		edgeButton.setActionCommand("EDGE");
		fillButton = new JRadioButton("Fill");
		// fillButton.setMnemonic(KeyEvent.VK_F);
		fillButton.setActionCommand("FILL");
		edgeFillGroup = new ButtonGroup();
		edgeFillGroup.add(edgeButton);
		edgeFillGroup.add(fillButton);
		
		// stroke slider
		strokeSlider = new JSlider(JSlider.VERTICAL, 
				 				   (int)DefaultSetting.minStroke, 
				 				   (int)DefaultSetting.maxStroke,
				 				   (int)DefaultSetting.stroke);
		strokeSlider.setMajorTickSpacing(3);
		strokeSlider.setPaintTicks(true);
		strokeSlider.setToolTipText("Just Silde It!");
		
		Hashtable<Integer, JLabel> strokeSliderHashtable = new Hashtable<>();
		strokeSliderHashtable.put(1, new JLabel("Thiner"));
		strokeSliderHashtable.put((int)DefaultSetting.maxStroke / 2, new JLabel("Middle"));
		strokeSliderHashtable.put((int)DefaultSetting.maxStroke, new JLabel("Thicker"));
		strokeSlider.setLabelTable(strokeSliderHashtable);
		strokeSlider.setPaintLabels(true);
	}
	
	private void createPanel()
	{
		mainPanel = new QPanel();
	}
	
}
