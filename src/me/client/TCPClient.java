package me.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import me.socketcommon.BroadcastMessage;
import me.socketcommon.MessageType;
import me.socketcommon.PostMessage;
import me.socketcommon.Sendable;

public class TCPClient implements AutoCloseable {

	public TCPClient(String inetAddress, int port) throws IOException
	{
		/*
		 * blocking
		 */
		socket = new Socket(inetAddress, port);
		sendQueue = new LinkedList<>();
		
		
		curMessageType = null;
		postMessage = new PostMessage();
		broadcastMessage = new BroadcastMessage();
		
		running = true;
	}
	
	public void bindDAO(DAO dao)
	{
		this.dao = dao;
		postMessage.addPostMessageListener(dao);
		broadcastMessage.addBroadcastMessageListener(dao);
	}
	
	public void terminate()
	{
		
	}
	
	/**
	 * main thread for receiving
	 * 
	 * inner class for sending 
	 */
	public void run()
	{
		try(InputStream is = socket.getInputStream();) {
			/**
			 * finite loop
			 * due to exception mechanism
			 */
			while(running) {
				// blocking most time
				ObjectInputStream ois = new ObjectInputStream(is);
				curMessageType = (MessageType)ois.readObject();
				if(curMessageType == MessageType.POST_MODEL) {
					postMessage.read(is);
					/**
					 * may be changed in View / APP
					 */
					synchronized(this.dao) { 
						postMessage.handle();
					}
				} else if(curMessageType == MessageType.BROAD_CAST_MODEL) {
					broadcastMessage.read(is);
					/**
					 * may be changed in View / APP
					 */
					synchronized(this.dao) {
						broadcastMessage.handle();
					}
				}
			}
		} catch(ClassNotFoundException | IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void addSendable(Sendable s)
	{
		synchronized(sendQueue) {
			sendQueue.add(s);
		}
	}
	
	@Override
	public void close() throws IOException
	{
		socket.close();
	}
	
	// mutual exclusion
	private DAO dao;
	
	// make sure chronologically
	// between receive & send
	private LinkedList<Sendable> sendQueue;
	
	private volatile boolean running = false;
	private Socket socket;
	private MessageType curMessageType;
	private PostMessage postMessage;
	private BroadcastMessage broadcastMessage;
}
