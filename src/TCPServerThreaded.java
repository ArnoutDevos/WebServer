import java.net.*;

import initLine.Handler;
//comment
//server should close the client socket.
class TCPServerThreaded
{
	public static void main(String argv[]) throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(6789);
		ThreadPool threadPool = new ThreadPool(10);
		while (true)
		{
			Socket connectionSocket = serverSocket.accept();
			if (connectionSocket != null)
			{
				Handler h = new Handler(connectionSocket);
				threadPool.execute(h);
//				Thread thread = new Thread(h);
//				thread.start();
			}
		}
	}
}