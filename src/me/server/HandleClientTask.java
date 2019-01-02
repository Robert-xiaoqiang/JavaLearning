package me.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.lang.instrument.Instrumentation;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import me.socketcommon.BroadcastMessage;
import me.socketcommon.IMessage;
import me.socketcommon.Sendable;
import me.socketcommon.MessageType;
import me.socketcommon.PostMessage;

public class HandleClientTask implements Runnable {
	
	// non-static inner class
	private class ClientSendTask implements Runnable {
		public ClientSendTask(OutputStream os) 
		{
			this.os = os;
		}
		
		@Override
		public void run()
		{
			synchronized(sendQueue) {
				while(true) { 
					if(!sendQueue.isEmpty()) {
						try {
							/**
							 * @note
							 * !!!
							 * synchronize outer class attribute
							 * wait for outer object
							 * !!!  
							 */
							HandleClientTask.this.wait();
							// release synchronized
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						Sendable s = sendQueue.poll();
						try {
							// just send one item
							s.send(os);
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		private OutputStream os;
	}
	
	public HandleClientTask(Socket socket, TCPServer server, DAO dao) throws IOException 
	{
		this.socket = socket;
		
		/**
		 *  new send thread
		 */
		sendThread = new Thread(new ClientSendTask(this.socket.getOutputStream()));
		sendThread.start();
		
		sendQueue = new LinkedList<>();
		curMessageType = null;
		
		/**
		 * dao from main thread
		 * make sure mutual exclusion
		 */
		this.server = server;
		this.dao = dao;
		postMessage = new PostMessage();
		postMessage.addPostMessageListener(dao);
		
		broadcastMessage = new BroadcastMessage();
		broadcastMessage.addPostMessageListener(dao);
	}
	
	/**
	 *  receive thread
	 *  
	 *  protocol APDU(type, content)
	 */
	@Override
	public void run()
	{
		try(InputStream is = socket.getInputStream();) {
			/**
			 * finite loop
			 * due to exception mechanism
			 */
			while(true) {
				// blocking most time
				ObjectInputStream ois = new ObjectInputStream(is);
				curMessageType = (MessageType)ois.readObject();
				if(curMessageType == MessageType.POST_MODEL) {
					postMessage.read(is);
					/**
					 * many threads
					 */
					synchronized(this.dao) { 
						postMessage.handle();
					}
					/**
					 * many threads
					 */
					synchronized(server) {
						server.broadcast(curMessageType, postMessage);
					}
				} else if(curMessageType == MessageType.BROAD_CAST_MODEL) {
					broadcastMessage.read(is);
					synchronized(this.dao) {
						broadcastMessage.handle();
					}
					synchronized(server) {
						server.broadcast(curMessageType, broadcastMessage);
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
			sendQueue.offer(s);
			// here is OK???
			// notifyAll();
		}
		notifyAll();
		/**
		 * awake send thread
		 * typical producer/consumer
		 */
	}
	
	// make sure chronologically
	// between receive & send
	private LinkedList<Sendable> sendQueue;
	
	private Thread sendThread;
	private Socket socket;
	
	// make sure mutual
	// between main & others
	private TCPServer server;
	private DAO dao;

	private MessageType curMessageType;
	private PostMessage postMessage;
	private BroadcastMessage broadcastMessage;
}
