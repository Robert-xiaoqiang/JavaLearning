package me.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import me.socketcommon.Sendable;

public class MDModel implements Serializable, Sendable {

	private final static long serialVersionUID = 1l;
	
	public MDModel(String mdText) 
	{
		this.mdText = new String(mdText);
	}
	
	public MDModel()
	{
		mdText = "";
	}
	
	// copy constructor
	public MDModel(MDModel model)
	{
		this.mdText = new String(model.mdText);
	}
	public void setMD(String mdText)
	{
		this.mdText = mdText;
	}
	
	public String getMD()
	{
		return mdText;
	}
	
	@Override
	public void send(OutputStream os) throws IOException
	{
		ObjectOutputStream ois = new ObjectOutputStream(os);
		ois.writeObject(this);
	}
	
	private String mdText;
}
