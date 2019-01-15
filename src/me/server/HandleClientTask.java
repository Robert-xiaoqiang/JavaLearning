package me.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
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
		private volatile boolean running = false;
		
		public ClientSendTask(ObjectOutputStream oos) 
		{
			this.oos = oos;
			running = true;
		}
		
		public void terminate()
		{
			running = false;
		}
		
		@Override
		public void run()
		{
			synchronized(sendQueue) {
				while(running) { 
					if(sendQueue.isEmpty()) {
						try {
							/**
							 * @note
							 * !!!
							 * synchronize outer class attribute
							 * wait for outer object
							 * !!!  
							 */
							sendQueue.wait();
							// release synchronized
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						Sendable s = sendQueue.poll();
						try {
							// just send one item
							s.send(oos);
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		private ObjectOutputStream oos;
	}
	
	public HandleClientTask(Socket socket, TCPServer server, DAO dao) throws IOException 
	{
		this.socket = socket;
		
		sendQueue = new LinkedList<>();
		/**
		 *  new send thread
		 */
		sendTask = new ClientSendTask(new ObjectOutputStream(this.socket.getOutputStream()));
		sendThread = new Thread(sendTask);
		sendThread.start();

		/**
		 * dao from main thread
		 * make sure mutual exclusion
		 */
		this.server = server; 			// bind server
		this.dao = dao;					// bind DAO
		curMessageType = null;
		postMessage = new PostMessage();
		postMessage.addPostMessageListener(dao);
		
		broadcastMessage = new BroadcastMessage();
		broadcastMessage.addBroadcastMessageListener(dao);
		
		running = true;
	}
	
	public void terminate() throws InterruptedException
	{
		running = false;
		/**
		 * request exit
		 */
		sendTask.terminate();
		/**
		 * wait for exiting
		 */
		sendThread.join();
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
			BufferedInputStream bii = new BufferedInputStream(is);
			ObjectInputStream ois = new ObjectInputStream(bii);
			while(running) {
				// blocking most time
//				bii.mark(1);
//				int byte1 = (int)bii.read();
//				bii.reset();
//				
//				if(byte1 == -1) {
//					try {
//						terminate();
//					} catch(InterruptedException ie) {
//						ie.printStackTrace();
//					}
//					break;
//				}
								
				curMessageType = (MessageType)ois.readObject();
				if(curMessageType == MessageType.POST_MODEL) {
					postMessage.read(ois);
					/**
					 * many threads
					 */
					postMessage.handle();
					/**
					 * many threads
					 */
					System.out.println("handle over a post message");
					synchronized(server) {
						server.broadcast(this, MessageType.BROAD_CAST_MODEL, new BroadcastMessage(postMessage));
					}
				} else if(curMessageType == MessageType.BROAD_CAST_MODEL) {
					broadcastMessage.read(ois);
					/**
					 * many threads
					 */
					broadcastMessage.handle();
				}
			}
		} catch(ClassNotFoundException | IOException ioe) {
			// ioe.printStackTrace();
			// exit dirty
			;;;;
		}
	}
	
	public void addSendable(Sendable s)
	{
		synchronized(sendQueue) {
			sendQueue.offer(s);
			// here is OK???
			// notifyAll();
			sendQueue.notifyAll();
		}
		/**
		 * awake send thread
		 * typical producer/consumer
		 */
	}
	
	// make sure chronologically
	// between receive & send
	private LinkedList<Sendable> sendQueue;
	
	private Thread sendThread;
	private ClientSendTask sendTask;
	private Socket socket;
	
	// make sure mutual
	// between main & others
	private TCPServer server;
	private DAO dao;

	private volatile boolean running = false;
	private MessageType curMessageType;
	private PostMessage postMessage;
	private BroadcastMessage broadcastMessage;
}
