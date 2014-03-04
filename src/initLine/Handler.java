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
		// echo server:
//		 String clientSentence = inFromClient.readLine();
//		 System.out.println("Received: " + clientSentence);
//		 String capsSentence = clientSentence.toUpperCase() + '\n';
//		 outToClient.writeBytes(capsSentence);
//		 connectionSocket.close();
//		 System.out.println("Connection closed.");
		boolean ready = false;
		String clientSentence = "";
		while(!ready){
			clientSentence += inFromClient.readLine() + "\r\n";
			System.out.println("Received: " + clientSentence);
			if(clientSentence.endsWith("b"))
				ready = true;
		}
		Command command = makeCommand(clientSentence);
		outToClient.writeBytes(command.execute() + "\n");
//		connectionSocket.close();
//		System.out.println("Connection closed.");
	}

	private Command makeCommand(String clientSentence) {
		//String[] tokens = clientSentence.split(" ");
		//String command = tokens[0].toUpperCase();
		String tmp = clientSentence.toUpperCase();
		if(tmp.startsWith("HEAD"))
			return new Head(clientSentence);
//		if(tmp.startsWith("GET"))
//			return new Get(clientSentence);
//		if(tmp.startsWith("PUT"))
//			return new Put(clientSentence);
//		if(tmp.startsWith("POST"))
//			return new Post(clientSentence);
		return new WrongCommand(clientSentence);
	}
}