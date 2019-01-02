package me.socketcommon;

import java.io.InputStream;

public interface IMessage extends Sendable {
	public void read(InputStream inStream);
	
	// poll listener and mutual and threads
	public void handle();
}
