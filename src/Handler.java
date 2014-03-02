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
		//Read the http request from the client into a buffered reader
		BufferedReader inFromClient = new BufferedReader(new
				InputStreamReader (connectionSocket.getInputStream()));
		//Create datastream from server to client
		DataOutputStream outToClient = new DataOutputStream
				(connectionSocket.getOutputStream());
		//Let another method do the input/output processing
		processIO(inFromClient, outToClient);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

	private void processIO(BufferedReader inFromClient,
			DataOutputStream outToClient){
		try {
			//Read the next line that the client submits
			String clientSentence = inFromClient.readLine();
			Head expr = makeExpression(clientSentence);
			System.out.println("Received: " + clientSentence);
			outToClient.writeBytes(expr.getResponse() + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private Head makeExpression(String clientSentence) {
		//String[] tokens = clientSentence.split(" ");
		//String command = tokens[0].toUpperCase();
		String tmp = clientSentence;
		tmp.toUpperCase();
		if(tmp.startsWith("HEAD"))
			return new Head(clientSentence);
		if(tmp.startsWith("GET"))
			return new Get(clientSentence);
		if(tmp.startsWith("PUT"))
			return new Put(clientSentence);
		if(tmp.startsWith("POST"))
			return new Post(clientSentence);
		return new WrongCommand(clientSentence);
	}
}