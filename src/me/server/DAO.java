package me.server;

import me.model.MDServerModel;
import me.socketcommon.BroadcastMessage;
import me.socketcommon.BroadcastMessageListener;
import me.socketcommon.PostMessage;
import me.socketcommon.PostMessageListener;

public class DAO implements PostMessageListener, BroadcastMessageListener {

	public DAO() 
	{
		mdModel = new MDServerModel();
	}
	
	/**
	 * for server accept post
	 */
	@Override
	public synchronized void receivedPostMessage(PostMessage pMsg)
	{
		mdModel.setMDText(pMsg.getMDText());
	}
	
	/**
	 * actually will not happen
	 */
	@Override
	public synchronized void receivedBroadcastMessage(BroadcastMessage bMsg)
	{
		;;;;
	}
	
	public synchronized MDServerModel getModel()
	{
		return mdModel;
	}
	
	private MDServerModel mdModel;
}
