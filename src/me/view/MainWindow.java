package me.view;


//Java Program to create a text editor using java 
import java.awt.*; 
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.LinkedList;
import java.awt.event.*; 
import javax.swing.plaf.metal.*; 
import javax.swing.text.*;

import com.github.rjeschke.txtmark.Processor;

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
	    setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
	   
	    setVisible(true);
	}
	
	// bind menu item
	public void bindDelete(ActionListener a)
	{
		deleteMenuItem.addActionListener(a);
	}
	
	public void bindDuplicate(ActionListener a)
	{
		duplicateMenuItem.addActionListener(a);
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
	// option
	private JMenuItem aboutMeMenuItem;
	// help
	private JMenuItem helpMenuItem;
	
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
	private JButton helpButton;
	
	// file operations
	private JFileChooser fc = null;
	private String fileName = null;
	
	// coupling model
	private JTextArea mdArea;
	private JEditorPane htmlArea;
	private JEditorPane tocArea;
	
	// model coupling
	private String mdText = "";
	
	private void initMenuBar()
	{
		// file
		fileMenu = new JMenu("File"); // no tear
		fileMenu.setBackground(Color.WHITE);
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		
		// edit
		editMenu = new JMenu("Edit"); // how about torn off
		editMenu.setBackground(Color.WHITE);
		editMenu.add(deleteMenuItem);
		editMenu.add(duplicateMenuItem);
		
		optionsMenu = new JMenu("Option");
		optionsMenu.setBackground(Color.WHITE);
		optionsMenu.add(aboutMeMenuItem);
		
		
		helpMenu = new JMenu("Help");
		helpMenu.setBackground(Color.WHITE);
		helpMenu.add(helpMenuItem);
		
		// menu bar
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
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
		toolBar.setBackground(Color.WHITE);
		toolBar.setBorderPainted(false);
		toolBar.add(openButton);
		toolBar.add(closeButton);
		toolBar.add(saveButton);
		toolBar.add(saveAsButton);
		toolBar.add(helpButton);
		
		// this
		add(toolBar, BorderLayout.NORTH);
	}
	
	
	private void initPanel()
	{
		tocArea = new JEditorPane();
		
		mdArea = new JTextArea();
		mdArea.setFont(new Font("non-Serif", Font.BOLD, 20));
		mdArea.setLineWrap(true);
		mdArea.setWrapStyleWord(true);
	    mdArea.setTabSize(1);
	    
		htmlArea = new JEditorPane();
		htmlArea.setFont(new Font("non-Serif", Font.ROMAN_BASELINE, 15));
		htmlArea.setContentType("text/html");
	    JScrollPane htmlScrollPane = new JScrollPane(htmlArea);
	    htmlScrollPane.setVerticalScrollBarPolicy(
	                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		mdArea.getDocument().addDocumentListener(new DocumentListener()
		{
			
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				try {
					Document document = e.getDocument();
					int length = document.getLength();
					String result = Processor.process(document.getText(0, length));
					System.out.println(document.getText(0, length));
					htmlArea.setText(result);	
				} catch(BadLocationException b) {
					b.printStackTrace();
				}			
			}
			
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				try {
					Document document = e.getDocument();
					int length = document.getLength();
					String result = Processor.process(document.getText(0, length));
					System.out.println(document.getText(0, length));
					htmlArea.setText(result);
				} catch(BadLocationException b) {
					b.printStackTrace();
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				try {
					Document document = e.getDocument();
					int length = document.getLength();
					String result = Processor.process(document.getText(0, length));
					System.out.println(document.getText(0, length));
					htmlArea.setText(result);	
				} catch(BadLocationException b) {
					b.printStackTrace();
				}
			}
		});
		
		JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		mainPanel.add(tocArea);
		mainPanel.add(mdArea);
		mainPanel.add(htmlScrollPane);
		// this
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void createMenuItem()
	{
		// file
		newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newMenuItem.addActionListener(a -> newFile());
		newMenuItem.setBackground(Color.WHITE);
		newMenuItem.setIcon(new ImageIcon(getClass().getResource("/new.png")));
		
		openMenuItem = new JMenuItem("Open", KeyEvent.VK_O);
		openMenuItem.setIcon(new ImageIcon(getClass().getResource("/open.png")));
		openMenuItem.setBackground(Color.WHITE);
		openMenuItem.addActionListener(a -> openFile());
		
		closeMenuItem = new JMenuItem("Close", KeyEvent.VK_C);
		closeMenuItem.addActionListener(a -> closeFile());
		closeMenuItem.setBackground(Color.WHITE);
		closeMenuItem.setIcon(new ImageIcon(getClass().getResource("/close.png")));
		
		saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
		saveMenuItem.addActionListener(a -> saveAsFile());
		saveMenuItem.setBackground(Color.WHITE);
		saveMenuItem.setIcon(new ImageIcon(getClass().getResource("/save.png")));
		
		saveAsMenuItem = new JMenuItem("Save As", KeyEvent.VK_V);
		saveAsMenuItem.addActionListener(a -> saveAsFile());
		saveAsMenuItem.setBackground(Color.WHITE);
		saveAsMenuItem.setIcon(new ImageIcon(getClass().getResource("/saveAs.png")));
		
		// edit
		deleteMenuItem = new JMenuItem("Delete", KeyEvent.VK_BACK_SPACE);
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
		deleteMenuItem.setBackground(Color.WHITE);
		deleteMenuItem.setIcon(new ImageIcon(getClass().getResource("/delete.png")));
		
		duplicateMenuItem = new JMenuItem("Duplicate", KeyEvent.VK_D);
		duplicateMenuItem.setBackground(Color.WHITE);
		duplicateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		duplicateMenuItem.setIcon(new ImageIcon(getClass().getResource("/duplicate.png")));
		// options
		aboutMeMenuItem = new JMenuItem("About Me");
		aboutMeMenuItem.setBackground(Color.WHITE);
		aboutMeMenuItem.addActionListener(a -> aboutMe());
		aboutMeMenuItem.setIcon(new ImageIcon(getClass().getResource("/option.png")));
		// help
		helpMenuItem = new JMenuItem("Help Me");
		helpMenuItem.setBackground(Color.WHITE);
		helpMenuItem.addActionListener(a -> help());
		helpMenuItem.setIcon(new ImageIcon(getClass().getResource("/help.png")));
	}
	
	private void createButton()
	{
		// Menu Item
		openButton = new JButton();
		openButton.setIcon(new ImageIcon(getClass().getResource("/open.png")));
		openButton.setBorderPainted(false);
		openButton.setFocusPainted(false);
		openButton.setBackground(Color.WHITE);
		openButton.addActionListener(a -> openFile());
		
		closeButton = new JButton();
		closeButton.setIcon(new ImageIcon(getClass().getResource("/close.png")));
		closeButton.setBorderPainted(false);
		closeButton.setFocusPainted(false);
		closeButton.setBackground(Color.WHITE);
		closeButton.addActionListener(a -> closeFile());
		
		saveButton = new JButton();
		saveButton.setIcon(new ImageIcon(getClass().getResource("/save.png")));
		saveButton.setBorderPainted(false);
		saveButton.setFocusPainted(false);
		saveButton.setBackground(Color.WHITE);
		saveButton.addActionListener(a -> saveAsFile());
		
		saveAsButton = new JButton();
		saveAsButton.setIcon(new ImageIcon(getClass().getResource("/saveAs.png")));
		saveAsButton.setBorderPainted(false);
		saveAsButton.setFocusPainted(false);
		saveAsButton.setBackground(Color.WHITE);
		saveAsButton.addActionListener(a -> saveAsFile());
		
		helpButton = new JButton();
		helpButton = new JButton();
		helpButton.setIcon(new ImageIcon(getClass().getResource("/help.png")));
		helpButton.setBorderPainted(false);
		helpButton.setFocusPainted(false);
		helpButton.setBackground(Color.WHITE);
		helpButton.addActionListener(a -> help());
	}

	private void createFileOperation()
	{
		fileName = "untitled";
		fc = new JFileChooser("./");
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("*.qinicad", "qinicad"));
	}
	
	private void newFile()
	{
		if(okToContinue()) {
			fileName = "untitled";
			// setCurrent
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
                		// anti-serialize
                		// setCurrent();
                	} catch(IOException ioe) {
                		ioe.printStackTrace();
                	}
                	/*catch(ClassNotFoundException cnfe) {
                		cnfe.printStackTrace();
                	}*/
                } else {
                	// open failed
                	// format failed
                	// file name failed
                }
            } 
		}
	}
	
	private void setCurrent()
	{
		
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
		return true;
	}
	
	/*
	 * never call it directly
	 * call saveAsFile() instead
	 */	
	private boolean saveFile()
	{
		// fileName must be set before here
		boolean ret = true;
		
		return ret;
	}
	
	private boolean saveAsFile()
	{
		if(fileName.equals("untitled")) {
			int returnVal = fc.showSaveDialog(this);
			 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                fileName = file.getName();
                if(fileName.indexOf(".qinicad") == -1) fileName += ".qinicad";
            }          
		} 
			
		return saveFile();
		
	}
	
	private void help()
	{
		JOptionPane.showMessageDialog(this,
			    "<html><h1>This is a JAD homework in Swing</h1>" +
			    "<h2>MVC paradigm</h2>" +
			    "<p>Markdown Client</p>",
			    "CoMDClient Help",
			    JOptionPane.INFORMATION_MESSAGE,
			    new ImageIcon(getClass().getResource("/help.png")));
	}
	
	private void aboutMe()
	{
		JOptionPane.showMessageDialog(this,
			    "<html><h1>CoMDClient v0.0.1</h1>" +
				"<h2>(C) QIndomitable</h2>" +
			    "<p>Enjoy Yourself</p></html>",
			    "CoMDClient Option",
			    JOptionPane.INFORMATION_MESSAGE,
			    new ImageIcon(getClass().getResource("/option.png")));
	}
	
}
