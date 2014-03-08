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
		processIO();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	

	private void processIO() throws IOException {

		
		String input = "";
		String clientSentence = "";
		while((input = inFromClient.readLine()) != null && !input.equals("")){
			clientSentence += input + " ";
		}
		System.out.println("Received: " + clientSentence);
	
		Command command = makeCommand(clientSentence);
		command.execute();
		System.out.println("after command.execute().");
		//The next two lines have to be comment when using our own client
		outToClient.close();
		inFromClient.close();
//		connectionSocket.close();
//		System.out.println("Connection closed.");
	}

	private Command makeCommand(String clientSentence) {
		String[] tokens = clientSentence.split(" ");
		String command = tokens[0].toUpperCase();
		if(wrongCommand(tokens, clientSentence))
			return new WrongCommand(tokens, outToClient);
		if(command.equals("HEAD"))
			return new Head(tokens, outToClient);
		if(command.equals("GET"))
			return new Get(tokens, outToClient);
		if(command.equals("PUT"))
			return new Put(tokens, outToClient, inFromClient);
		if(command.equals("POST"))
			return new Post(tokens, outToClient, inFromClient);
		return new WrongCommand(tokens, outToClient);
	}
	
	//fault with http version
	private boolean wrongCommand(String[] tokens, String clientSentence){
		if(tokens.length < 3 || (tokens[2].equals("HTTP/1.1") && !clientSentence.contains("Host:")) ||
				(!tokens[2].equals("HTTP/1.0") && !tokens[2].equals("HTTP/1.1")))
			return true;
		return false;
	}
}