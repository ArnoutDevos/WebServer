import java.io.*;
import java.net.*;

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
		System.out.println("Received: " + clientSentence);
		String capsSentence = clientSentence.toUpperCase() + '\n';
		outToClient.writeBytes(capsSentence);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}