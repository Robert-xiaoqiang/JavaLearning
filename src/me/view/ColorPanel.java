package me.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import me.common.event.ColorChangedListener;

class ColorPanel extends JPanel {
	public ColorPanel() 
	{
		createButton();
		initButton();
		addListerner();
	}
	
	@Override
	public void paintComponents(Graphics g) 
	{
		super.paintComponents(g);
	}
	
	public void addEdgeColorChangeListener(ColorChangedListener e)
	{
		edgeColorListener = e;
	}
	
	public void addFillColorChangedListener(ColorChangedListener f)
	{
		fillColorListener = f;
	}
	
	private void createButton()
	{
		redEdgeButton = new JButton();
		greenEdgeButton = new JButton();
		blueEdgeButton = new JButton();
		cyanEdgeButton = new JButton();
		grayEdgeButton = new JButton();
		pinkEdgeButton = new JButton();
		blackEdgeButton = new JButton();
		orangeEdgeButton = new JButton();
		magentaEdgeButton = new JButton();

		redEdgeButton.setBackground(Color.red);
		greenEdgeButton.setBackground(Color.green);
		blueEdgeButton.setBackground(Color.blue);
		cyanEdgeButton.setBackground(Color.cyan);
		grayEdgeButton.setBackground(Color.gray);
		pinkEdgeButton.setBackground(Color.pink);
		blackEdgeButton.setBackground(Color.black);
		orangeEdgeButton.setBackground(Color.orange);
		magentaEdgeButton.setBackground(Color.magenta);
		
		redFillButton = new JButton();
		greenFillButton = new JButton();
		blueFillButton = new JButton();
		cyanFillButton = new JButton();
		grayFillButton = new JButton();
		pinkFillButton = new JButton();
		blackFillButton = new JButton();
		orangeFillButton = new JButton();
		magentaFillButton = new JButton();
					
		redFillButton.setBackground(Color.red);
		greenFillButton.setBackground(Color.green);
		blueFillButton.setBackground(Color.blue);
		cyanFillButton.setBackground(Color.cyan);
		grayFillButton.setBackground(Color.gray);
		pinkFillButton.setBackground(Color.pink);
		blackFillButton.setBackground(Color.black);
		orangeFillButton.setBackground(Color.orange);
		magentaFillButton.setBackground(Color.magenta);
	}
	
	private void initButton()
	{
		JPanel edgePanel = new JPanel();
		edgePanel.setLayout(new GridLayout(3, 3));
		edgePanel.setBorder(new TitledBorder("Edge Color"));
		edgePanel.add(redEdgeButton);
		edgePanel.add(greenEdgeButton);
		edgePanel.add(blueEdgeButton);
		edgePanel.add(cyanEdgeButton);
		edgePanel.add(grayEdgeButton);
		edgePanel.add(pinkEdgeButton);
		edgePanel.add(blackEdgeButton);
		edgePanel.add(orangeEdgeButton);
		edgePanel.add(magentaEdgeButton);
		
		JPanel fillPanel = new JPanel();
		fillPanel.setLayout(new GridLayout(3, 3));
		fillPanel.setBorder(new TitledBorder("Fill Color"));
		fillPanel.add(redFillButton);
		fillPanel.add(greenFillButton);
		fillPanel.add(blueFillButton);
		fillPanel.add(cyanFillButton);
		fillPanel.add(grayFillButton);
		fillPanel.add(pinkFillButton);
		fillPanel.add(blackFillButton);
		fillPanel.add(orangeFillButton);
		fillPanel.add(magentaFillButton);
		
		// this
		setLayout(new GridLayout(1, 2));
		setBorder(new TitledBorder("Color Panel"));
		add(edgePanel);
		add(fillPanel);
	}
	
	private void addListerner()
	{
		redEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.RED;
			
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		greenEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.GREEN;
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		blueEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.BLUE;	
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		cyanEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.CYAN;	
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		grayEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.GRAY;	
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		pinkEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.PINK;	
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		blackEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.BLACK;	
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		orangeEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.ORANGE;
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		magentaEdgeButton.addActionListener((e) -> 
		{
			edgeColor = Color.MAGENTA;
			// notify
			// just singleton
			edgeColorListener.onColorChanged(edgeColor);
		});
		
		////////////////////////////////////////////////~fill~/
		redFillButton.addActionListener((e) ->
		{
			fillColor = Color.RED;
			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		greenFillButton.addActionListener((e) ->
		{
			fillColor = Color.GREEN;
			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		blueFillButton.addActionListener((e) ->
		{
			fillColor = Color.BLUE;
			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		cyanFillButton.addActionListener((e) ->
		{
			fillColor = Color.CYAN;
			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		grayFillButton.addActionListener((e) ->
		{
			fillColor = Color.GRAY;			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		pinkFillButton.addActionListener((e) ->
		{
			fillColor = Color.PINK;
			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		blackFillButton.addActionListener((e) ->
		{
			fillColor = Color.BLACK;
			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		orangeFillButton.addActionListener((e) ->
		{
			fillColor = Color.ORANGE;			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
		magentaFillButton.addActionListener((e) ->
		{
			fillColor = Color.MAGENTA;			// notify
			// just singleton
			fillColorListener.onColorChanged(fillColor);
		});
	}
	
	// naive listener
	// singleton just
	// threads is not necessary
	private ColorChangedListener edgeColorListener;
	private ColorChangedListener fillColorListener;
	
	private Color edgeColor;
	private Color fillColor; 
	
	private JButton redEdgeButton;
	private JButton greenEdgeButton;
	private JButton blueEdgeButton;
	private JButton cyanEdgeButton;
	private JButton grayEdgeButton;
	private JButton pinkEdgeButton;
	private JButton blackEdgeButton;
	private JButton orangeEdgeButton;
	private JButton magentaEdgeButton;

	private JButton redFillButton;
	private JButton greenFillButton;
	private JButton blueFillButton;
	private JButton cyanFillButton;
	private JButton grayFillButton;
	private JButton pinkFillButton;
	private JButton blackFillButton;
	private JButton orangeFillButton;
	private JButton magentaFillButton;
	
}
