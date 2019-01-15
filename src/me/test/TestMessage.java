package me.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.socketcommon.MessageType;

public class TestMessage {
	public TestMessage() 
	{
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		MessageType type = MessageType.POST_MODEL;
		ByteArrayOutputStream bArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(bArrayOutputStream);
		objectOutputStream.writeObject(type);
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrayOutputStream.toByteArray());
		ObjectInputStream objectInputStream2 = new ObjectInputStream(byteArrayInputStream);
		MessageType type2 = (MessageType)objectInputStream2.readObject();
		System.out.println(type2);
	}

}
