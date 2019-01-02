package me.socketcommon;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import me.model.MDModel;

public class PostMessage implements IMessage {

	public PostMessage() 
	{
		postMessageListeners = new ArrayList<>();
		model = null;
	}
	/**
	 * copy model
	 * do not copy listener
	 * @param p
	 */
	public PostMessage(PostMessage p)
	{
		postMessageListeners = new ArrayList<>();
		this.model = new MDModel(p.model);
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
		// copy many message
		// send separately
		synchronized(this) {
			for(final PostMessageListener pml : postMessageListeners) {
				new Thread(() -> pml.receivedPostMessage(new PostMessage(this))).start();
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
	public synchronized void addPostMessageListener(PostMessageListener postMessageListener)
	{
		postMessageListeners.add(postMessageListener);
	}
	
	// remove ** Listener
	public synchronized void removePostMessageListener(PostMessageListener postMessageListener)
	{
		postMessageListeners.remove(postMessageListener);
	}
	
	private ArrayList<PostMessageListener> postMessageListeners;
	private MDModel model;
}
