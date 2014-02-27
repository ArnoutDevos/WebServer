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
		Head expr = makeExpression(clientSentence);
		System.out.println("Received: " + clientSentence);
		//String capsSentence = clientSentence.toUpperCase() + '\n';
		outToClient.writeBytes(expr.getResponse() + '\n');
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

	private Head makeExpression(String clientSentence) {
		String[] tokens = clientSentence.split(" ");
		String command = tokens[0].toUpperCase();
		if(command.equals("HEAD"))
			return new Head(tokens);
		if(command.equals("GET"))
			return new Get(tokens);
		if(command.equals("PUT"))
			return new Put(tokens);
		if(command.equals("POST"))
			return new Post(tokens);
		return new WrongCommand(tokens);
	}
}