package me.socketcommon;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

public interface Sendable {
	public void send(ObjectOutputStream os) throws IOException, ClassNotFoundException;
	public void sendBySocketChannel(SocketChannel socketChannel) throws IOException, ClassNotFoundException;
}
