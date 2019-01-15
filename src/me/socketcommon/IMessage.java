package me.socketcommon;

import java.io.InputStream;
import java.io.ObjectInputStream;

public interface IMessage extends Sendable {
	public void read(ObjectInputStream ois);
	
	// poll listener and mutual and threads
	public void handle();
}
