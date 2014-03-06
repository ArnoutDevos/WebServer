package initLine;

import java.io.*;
import java.net.*;



public class Handler implements Runnable
{
	Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	
	public Handler(Socket socket) { 
		this.connectionSocket = socket;
	}
	
	@Override
	public void run()
	{
		try{
		//Read the http request from the client into a buffered reader
		this.inFromClient = new BufferedReader(new
				InputStreamReader (connectionSocket.getInputStream()));
		//Create datastream from server to client
		this.outToClient = new DataOutputStream
				(connectionSocket.getOutputStream());
		//Let another method do the input/output processin
		processIO(inFromClient, outToClient);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	

	private void processIO(BufferedReader inFromClient, DataOutputStream outToClient) throws IOException {

		
		String input = "";
		String clientSentence = "";
		while(!(input = inFromClient.readLine()).equals("")){
			clientSentence += input + " ";
		}
		System.out.println("Received: " + clientSentence);
		
		Command command = makeCommand(clientSentence);
		command.execute();
//		connectionSocket.close();
//		System.out.println("Connection closed.");
	}

	private Command makeCommand(String clientSentence) {
		String[] tokens = clientSentence.split(" ");
		String command = tokens[0].toUpperCase();
		if(command.equals("HEAD"))
			return new Head(tokens, outToClient);
		if(command.equals("GET"))
			return new Get(tokens, outToClient);
		if(command.equals("PUT"))
			return new Put(tokens, inFromClient, outToClient);
		if(command.equals("POST"))
			return new Post(tokens, inFromClient, outToClient);
		return new WrongCommand(tokens, inFromClient, outToClient);
	}
}