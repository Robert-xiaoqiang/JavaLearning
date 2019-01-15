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

public class PostMessage implements IMessage {

	public PostMessage() 
	{
		postMessageListeners = new ArrayList<>();
		mdText = null;
	}
	
	/**
	 * copy model
	 * do not copy listener
	 * @param p
	 */
	public PostMessage(PostMessage another)
	{
		postMessageListeners = new ArrayList<>();
		this.mdText = new String(another.mdText);
	}
	
	public PostMessage(BroadcastMessage broadcastMessage)
	{
		postMessageListeners = new ArrayList<>();
		this.mdText = new String(broadcastMessage.getMDText());		
	}
	
	public PostMessage(String mdText)
	{
		postMessageListeners = new ArrayList<>();
		this.mdText = new String(mdText);
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
		// copy many message
		// send separately
		synchronized(this) {
			for(final PostMessageListener pml : postMessageListeners) {
				new Thread(() -> pml.receivedPostMessage(new PostMessage(this))).start();
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
	private String mdText;
}
