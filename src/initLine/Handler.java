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
		//Let another method do the input/output processing
		while(!connectionSocket.isClosed())
			processIO();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	

	/**
	 * This method processes the input and sends back the appropriate output.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void processIO() throws IOException, InterruptedException {

		String input = "";
		String clientSentence = "";
		//If the client hasn't send a line yet, wait.
		while(!inFromClient.ready())
			Thread.sleep(100);
		//Read the input from the client.
		while((input = inFromClient.readLine()) != null && !input.equals("")){
			clientSentence += input + " ";
		}
		System.out.println("Received: " + clientSentence);
		String[] tokens = clientSentence.split(" ");
		Command command = makeCommand(tokens, clientSentence);
		command.execute();
		outToClient.writeBytes("\r\n\r\n");
		outToClient.flush();
		//If the HTTP-version is 1.0 close the connection.
		if(!isHTTP11(tokens)){
			outToClient.close();
			inFromClient.close();
		}
	}

	/**
	 * This method makes a new object of the command-class according to the given input. There
	 * are five different command-classes: WrongCommand, Head, Get, Put and Post. The WrongCommand-class
	 * is chosen if the input from the client is not correct.
	 * 
	 * @param tokens
	 * @param clientSentence
	 * @return	An instance of a command-class.
	 */
	private Command makeCommand(String[] tokens, String clientSentence) {
		String command = tokens[0];
		if(wrongSyntax(tokens, clientSentence))
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
	
	/**
	 * This method checks whether the syntax of the input is correct or not.
	 * 
	 * @param tokens
	 * @param clientSentence
	 * @return	True if something with the Syntax is wrong.
	 */
	private boolean wrongSyntax(String[] tokens, String clientSentence){
		if( !hasMinLength(tokens) || (isHTTP11(tokens) && !clientSentence.contains("Host:")) ||
				(!tokens[2].equals("HTTP/1.0") && !tokens[2].equals("HTTP/1.1")))
			return true;
		return false;
	}
	
	/**
	 * This method checks if the HTTP-version is 1.1 
	 * 
	 * @param tokens
	 * @return
	 */
	private boolean isHTTP11(String[] tokens){
		if(tokens.length < 3 || !(tokens[2].equals("HTTP/1.1")))
			return false;
		return true;
	}
	
	/**
	 * This method checks if the input has at least the minimum number of arguments.
	 * 
	 * @return	True if the input has at least the minimum length.
	 */
	public boolean hasMinLength(String[] tokens){
		return !(tokens.length < 3);
	}
}