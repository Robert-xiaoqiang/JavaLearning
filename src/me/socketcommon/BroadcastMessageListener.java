package me.socketcommon;

public interface BroadcastMessageListener extends IMessageListener {
	public void receivedBroadcastMessage(BroadcastMessage bMsg);
}
