package me.socketcommon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public enum MessageType implements Sendable, Serializable {
	// client => server
	POST_MODEL,
	// server => client
	BROAD_CAST_MODEL;
	
	@Override
	public void send(ObjectOutputStream oos) throws IOException
	{
		oos.writeObject(this);
	}
	
	@Override
	public void sendBySocketChannel(SocketChannel socketChannel) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		socketChannel.write(ByteBuffer.wrap(baos.toByteArray()));
	}
}
