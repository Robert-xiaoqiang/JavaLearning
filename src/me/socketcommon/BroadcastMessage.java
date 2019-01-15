package me.socketcommon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class BroadcastMessage implements IMessage {

	public BroadcastMessage() 
	{
		broadcastMessageListeners = new ArrayList<>();
		mdText = null;
	}
	
	public BroadcastMessage(BroadcastMessage another)
	{
		broadcastMessageListeners = new ArrayList<>();
		mdText = new String(another.mdText);
	}
	
	public BroadcastMessage(PostMessage postMessage)
	{
		broadcastMessageListeners = new ArrayList<>();
		mdText = new String(postMessage.getMDText());
	}
	
	public BroadcastMessage(String mdText)
	{
		broadcastMessageListeners = new ArrayList<>();
		mdText = new String(mdText);
	}
	
	@Override
	public void read(ObjectInputStream ois)
	{
		try {
			mdText = (String)ois.readObject();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}		
	}
	
	@Override
	public void handle()
	{
		// * listener may be changed outer object
		synchronized(this) {
			for(final BroadcastMessageListener pml : broadcastMessageListeners) {
				new Thread(() -> pml.receivedBroadcastMessage(new BroadcastMessage(this))).start();
			}			
		}
	}
	
	@Override
	public void send(ObjectOutputStream oos) throws IOException, ClassNotFoundException
	{
		oos.writeObject(mdText);
	}	
	
	@Override
	public void sendBySocketChannel(SocketChannel socketChannel) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(mdText);
		socketChannel.write(ByteBuffer.wrap(baos.toByteArray()));
	}
	
	public String getMDText()
	{
		return mdText;
	}	
	
	// register call back
	public synchronized void addBroadcastMessageListener(BroadcastMessageListener broadcastMessageListener)
	{
		broadcastMessageListeners.add(broadcastMessageListener);
	}
	
	// remove ** Listener
	public synchronized void removeBroadcastMessageListener(BroadcastMessageListener broadcastMessageListener)
	{
		broadcastMessageListeners.remove(broadcastMessageListener);
	}
	
	private ArrayList<BroadcastMessageListener> broadcastMessageListeners;
	private String mdText;

}
