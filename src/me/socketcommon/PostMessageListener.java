package me.socketcommon;

public interface PostMessageListener extends IMessageListener {
	// different base interface
	public void receivedPostMessage(PostMessage pMsg);
}
