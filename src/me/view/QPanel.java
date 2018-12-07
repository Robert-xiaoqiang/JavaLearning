package me.view;

import java.awt.Graphics;
import javax.swing.JPanel;
import me.model.Pages;

public class QPanel extends JPanel {
	
	public QPanel() 
	{
		super();
		pages = null;
	}
	
	public void bindModel(Pages pages)
	{
		this.pages = pages;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		pages.render(g);
	}
	
	private Pages pages;
}
