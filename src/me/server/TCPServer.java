package me.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import me.socketcommon.PostMessage;
import me.socketcommon.PostMessageListener;
import me.socketcommon.Sendable;

public class TCPServer implements AutoCloseable {
	public TCPServer(int port) throws IOException 
	{
		listenSocket = new ServerSocket(port);
		clientsMap = new Hashtable<>();
		clientsTask = new ArrayList<>();
		dao = new DAO();
	}
		
	public void run() throws IOException
	{
		/**
		 * shell thread
		 * for server
		 * exiting positively 
		 */
		while(true) {
			Socket connectSocket = listenSocket.accept();
			/**
			 * DAO is interested in clients and their messages
			 */
			HandleClientTask clientTask = new HandleClientTask(connectSocket, this, dao);
			/**
			 * no broadcast
			 * make sure
			 */
			synchronized(clientsTask) {
				clientsTask.add(clientTask);	
			}
			
			Thread clientHandleThread = new Thread(clientTask);
			/**
			 * no broadcast
			 * make sure
			 */
			synchronized(clientsMap) {
				clientsMap.put(connectSocket, clientHandleThread);	
			}
			clientHandleThread.start();
		}
	}
	
	/**
	 * may be reentrant
	 * clients handle task may be
	 * call it in many threads
	 */
	public synchronized void broadcast(final Sendable type, final Sendable message)
	{
		/**
		 * !!!
		 * may be some thread not start
		 * send queue is not empty
		 * !!!
		 */
		// copy the list
		final ArrayList<HandleClientTask> copyList = new ArrayList<>();
		for(HandleClientTask h : clientsTask) {
			copyList.add(h);
		}
		
		// temporary sending thread
		new Thread(() ->
		{
			for(HandleClientTask h : copyList) {
				h.addSendable(type);
				h.addSendable(message);
			}
		}).start();
	}
	
	@Override
	public void close() throws IOException, InterruptedException
	{
		listenSocket.close();
		for(Map.Entry<Socket, Thread> entry : clientsMap.entrySet()) {
			entry.getValue().join();
			entry.getKey().close();
		}
	}
	
	private DAO dao;
	private ServerSocket listenSocket;
	private Hashtable<Socket, Thread> clientsMap;
	private ArrayList<HandleClientTask> clientsTask;

}
