import java.io.*;
import java.net.*;
import Expressions.*;

class Handler implements Runnable
{
	Socket connectionSocket;
	public Handler(Socket socket)
	{ this.connectionSocket = socket; }
	
	@Override
	public void run()
	{
		try{
		BufferedReader inFromClient = new BufferedReader(new
				InputStreamReader (connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream
				(connectionSocket.getOutputStream());
		String clientSentence = inFromClient.readLine();
		Head head = new Head("/path/example.html","HTTP/1.0");
		
		System.out.println("Received: " + clientSentence);
		//String capsSentence = clientSentence.toUpperCase() + '\n';
		outToClient.writeBytes(head.getResponse() + '\n');
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}