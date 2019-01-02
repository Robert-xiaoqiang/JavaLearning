package me.client;

import me.model.MDModel;
import me.socketcommon.BroadcastMessage;
import me.socketcommon.BroadcastMessageListener;
import me.socketcommon.PostMessage;
import me.socketcommon.PostMessageListener;

public class DAO implements PostMessageListener, BroadcastMessageListener {

	public DAO() 
	{
		mdModel = new MDModel();
	}
	
	/**
	 * for server receive post
	 * client will not happen actually
	 */
	@Override
	public void receivedPostMessage(PostMessage pMsg)
	{
		;;;;
	}
	
	/**
	 * client 
	 */
	@Override
	public void receivedBroadcastMessage(BroadcastMessage bMsg)
	{
		;;;;
	}
	
	public MDModel getModel()
	{
		return mdModel;
	}
	
	private MDModel mdModel;
}
