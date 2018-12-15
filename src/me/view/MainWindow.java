/**
 * 
 */
package me.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;

import com.sun.java.swing.plaf.windows.resources.windows;

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
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.OpenType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;


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
		// file operations
		createFileOperation();
		
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
		functionPanel.bindStateMachine(this.current);
	}
	
	public void bindModel(LinkedList<Pages> model)
	{
		this.model = model;
		Pages curPages = this.model.getLast();
		
		// dynamic update in new/open/close
		mainPanel.bindModel(curPages);		
		// bind sinks and notification
		curPages.addPropertyNotification(this.getViewSink());
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
	// bind menu item
	public void bindDelete(ActionListener a)
	{
		deleteMenuItem.addActionListener(a);
	}
	
	public void bindOpen(ActionListener a)
	{
		openMenuItem.addActionListener(a);
	}	
	
	public void bindSave(ActionListener a)
	{
		saveMenuItem.addActionListener(a);
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
	// file
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem closeMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	// edit
	private JMenuItem deleteMenuItem;
	private JMenuItem duplicateMenuItem;
	
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
	private LinkedList<Pages> model; 
	
	// panel component
	private QMainPanel mainPanel;

	// function panel component
	private QFunctionPanel functionPanel;
	
	// sinks
	private ViewPropertySink viewSink = null;
	
	// file operations
	private JFileChooser fc = null;
	private String fileName = null;
	/**
	 * 
	 */
	private void initMenuBar()
	{
		// file
		fileMenu = new JMenu("File"); // no tear
		fileMenu.add(openMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		
		// edit
		editMenu = new JMenu("Edit"); // how about torn off
		editMenu.add(deleteMenuItem);
		editMenu.add(duplicateMenuItem);
		
		optionsMenu = new JMenu("Option");
		helpMenu = new JMenu("Help");
		
		// menu bar
		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
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
		newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
		newMenuItem.addActionListener(a -> newFile());
		openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
		openMenuItem.addActionListener(a -> openFile());
		closeMenuItem = new JMenuItem("Close", KeyEvent.VK_C);
		closeMenuItem.addActionListener(a -> closeFile());
		saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
		saveMenuItem.addActionListener(a -> saveAsFile());
		saveAsMenuItem = new JMenuItem("Save As", KeyEvent.VK_V);
		saveAsMenuItem.addActionListener(a -> saveAsFile());
		// edit
		deleteMenuItem = new JMenuItem("Delete", KeyEvent.VK_BACK_SPACE);
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
		duplicateMenuItem = new JMenuItem("Duplicate", KeyEvent.VK_D);
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
		// mainPanel.setFocusable(true);
		functionPanel = new QFunctionPanel();
	}
	
	private void createFileOperation()
	{
		fileName = "untitled";
		fc = new JFileChooser("./");
	}
	
	private void newFile()
	{
		if(okToContinue()) {
			fileName = "untitled";
			setCurrent(new Pages()); // must be unmodified
		}
	}
	
	// file operations
	private void openFile()
	{
		if(okToContinue()) {
			int returnVal = fc.showOpenDialog(this);
			 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                fileName = file.getName(); // must be unmodified
                if(!fileName.isEmpty()) {
                	try(ObjectInputStream ois = new ObjectInputStream(
                			new BufferedInputStream(
                			new FileInputStream(fileName)));) {
                		Pages curPages = (Pages)ois.readObject();
                		setCurrent(curPages);
                	} catch(IOException ioe) {
                		ioe.printStackTrace();
                	} catch(ClassNotFoundException cnfe) {
                		cnfe.printStackTrace();
                	}
                } else {
                	// open failed
                	// format failed
                	// file name failed
                }
            } 
		}
	}
	
	private void setCurrent(Pages curPages)
	{
		// dynamic update in open/close
		mainPanel.bindModel(curPages);		
		// bind sinks and notification
		curPages.addPropertyNotification(this.getViewSink());
		// last pages => rubbish
		model.pop();
		model.push(curPages);
		
		// new/open 
		// repaint model
		// necessary
		this.update();
	}
	
	private void closeFile()
	{
		newFile();
	}
	/**
	 * @return
	 * else => false
	 * yes && save successfully => true
	 */
	private boolean okToContinue()
	{
		boolean ret = true;
		Pages curPages = model.getLast();
		// fileName (untitled or others)
		if(curPages.modelIsModified()) {
			ret = false;
			Object[] options = {"Yes, please", "No way!"};
			int n = JOptionPane.showOptionDialog(this, 	      // parent
											     "Would you like save your changes?",
												 "Unsaved File",
												 JOptionPane.YES_NO_OPTION,
												 JOptionPane.QUESTION_MESSAGE,
												 null,        // do not use a custom Icon
												 options,     // the titles of buttons
												 options[0]); // default button title
			if(n == JOptionPane.YES_OPTION) {
				ret = saveFile();
			}
		}
		return ret;
	}
	
	
	private boolean saveFile()
	{
		// fileName must be set before here
		boolean ret = true;
		Pages curPages = model.getLast();
		if(fileName.isEmpty()) ret = false; 
		try(ObjectOutputStream oos = new ObjectOutputStream(
				 new BufferedOutputStream(
				 new FileOutputStream(fileName)));) {
			oos.writeObject(curPages);
			curPages.setIsModified(false);
		} catch(IOException e) {
			e.printStackTrace();
			ret = false;
		}
		// AutoCloseable implements
		
		return ret;
	}
	
	private boolean saveAsFile()
	{
		if(fileName.equals("untitled")) {
			int returnVal = fc.showSaveDialog(this);
			 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                fileName = file.getName();
            }          
		} 
			
		return saveFile();
		
	}
}
