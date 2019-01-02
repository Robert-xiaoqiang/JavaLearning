package me.server;

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
	 * for server accept post
	 */
	@Override
	public void receivedPostMessage(PostMessage pMsg)
	{
		mdModel.setMD(pMsg.getModel().getMD());
	}
	
	/**
	 * actually will not happen
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
