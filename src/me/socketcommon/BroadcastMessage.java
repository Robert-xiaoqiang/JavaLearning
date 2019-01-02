package me.socketcommon;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import me.model.MDModel;

public class BroadcastMessage implements IMessage {

	public BroadcastMessage() 
	{
		broadcastMessageListeners = new ArrayList<>();
		model = null;
	}
	
	@Override
	public void read(InputStream in)
	{
		try {
			ObjectInputStream ois = new ObjectInputStream(in);
			model = (MDModel)ois.readObject();
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
				new Thread(() -> pml.receivedBroadcastMessage(this)).start();
			}			
		}
	}
	
	@Override
	public void send(OutputStream os) throws IOException, ClassNotFoundException
	{
		ObjectOutputStream ois = new ObjectOutputStream(os);
		ois.writeObject(this);
	}	
	
	public MDModel getModel()
	{
		return model;
	}	
	
	// register call back
	public synchronized void addPostMessageListener(BroadcastMessageListener broadcastMessageListener)
	{
		broadcastMessageListeners.add(broadcastMessageListener);
	}
	
	// remove ** Listener
	public synchronized void removePostMessageListener(BroadcastMessageListener broadcastMessageListener)
	{
		broadcastMessageListeners.remove(broadcastMessageListener);
	}
	
	private ArrayList<BroadcastMessageListener> broadcastMessageListeners;
	private MDModel model;

}
