package me.socketcommon;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public enum MessageType implements Sendable {
	// client => server
	POST_MODEL,
	// server => client
	BROAD_CAST_MODEL;
	
	@Override
	public void send(OutputStream os) throws IOException
	{
		ObjectOutputStream ois = new ObjectOutputStream(os);
		ois.writeObject(this);
	}
}
