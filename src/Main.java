import java.io.IOException;

import me.server.TCPServer;;

public class Main {
	public Main() 
	{
	
	}
	
	public static void main(String[] args)
	{
		try(TCPServer tcpServer = new TCPServer(23333);) {
			tcpServer.run();
		} catch(IOException | InterruptedException ioe) {
			ioe.printStackTrace();
		}
	}

}
