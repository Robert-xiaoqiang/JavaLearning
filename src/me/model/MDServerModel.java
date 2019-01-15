package me.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import me.socketcommon.Sendable;

public class MDServerModel implements Serializable {

	private final static long serialVersionUID = 1l;
	
	public MDServerModel(String mdText) 
	{
		this.mdText = new String(mdText);
	}
	
	public MDServerModel()
	{
		mdText = "";
	}
	
	// copy constructor
	public MDServerModel(MDServerModel model)
	{
		this.mdText = new String(model.mdText);
	}
	
	// construct from String
	public void setMDText(String mdText)
	{
		this.mdText = mdText;

	}
	
	public String getMDText()
	{
		return mdText;
	}
	
	private String mdText;
}
