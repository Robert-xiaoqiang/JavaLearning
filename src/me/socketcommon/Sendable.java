package me.socketcommon;

import java.io.IOException;
import java.io.OutputStream;

public interface Sendable {
	public void send(OutputStream os) throws IOException, ClassNotFoundException;
}
