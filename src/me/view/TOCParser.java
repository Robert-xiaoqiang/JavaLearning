package me.view;

import java.util.ArrayList;

class Entity {
	public Entity(String name)
	{
		this.name = name;
		this.body = new ArrayList<>();
	}
	
	@Override
	public String toString()
	{
		StringBuffer sBuffer = new StringBuffer();
		final String indent = "  ";
		sBuffer.append("<li>");
		sBuffer.append(name + "\n");
		if(!body.isEmpty()) {
			sBuffer.append(indent + "<ul>\n");
			for(Entity e : body) {
				sBuffer.append(e);
			}
			sBuffer.append(indent + "</ul>\n");
		}
		sBuffer.append("</li>\n");
		
		return sBuffer.toString();
	}
	ArrayList<Entity> body;
	String name;
}

public class TOCParser {

	public TOCParser() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public String getTOC(String md)
	{
		return "";
	}

}
