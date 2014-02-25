import java.io.*;
import java.net.*;
class TCPServerThreaded
{
	public static void main(String argv[]) throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(6789);
		while (true)
		{
			Socket connectionSocket = serverSocket.accept();
			if (connectionSocket != null)
			{
				Handler h = new Handler(connectionSocket);
				Thread thread = new Thread(h);
				thread.start();
			}
		}
	}
}