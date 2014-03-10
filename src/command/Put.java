package command;

import java.io.*;

/**
 * This class represents a Put-command. It inherits from the Head-command.
 * With this class you can put a new file on the server.
 * 
 * @author Jakob
 *
 */
public class Put extends Head {

	/**
	 * Variable representing the inputStream from the client.
	 */
	protected BufferedReader inFromClient;
	
	/**
	 * Variable representing the body of the input, e.g. the text that needs to be stored
	 * in the specified file.
	 */
	protected String body;
	
	/**
	 * Variable that indicates whether the file-creation was succesful.
	 */
	protected boolean success;
	
	/**
	 * Constructor of the Put-command.
	 * 
	 * @param clientSentence
	 * 			An array of all tokens from the command and the given header.
	 * @param outToClient
	 * 			The DataOutputStream going back to the client.
	 * @param inFromClient
	 * 			The BufferedReader for reading the body of the input.
	 */
	public Put(String[] clientSentence, DataOutputStream outToClient, BufferedReader inFromClient){
		super(clientSentence, outToClient);
		this.inFromClient = inFromClient;
	}
	
	/**
	 * This class generates the correct response depending on whether the file was
	 * put successfully or not.
	 */
	@Override
	public String getResponse() {
		String output = clientSentence[2] + " ";
		if(success){
			output += "201 Created";
		} else
			output += "500 Server Error";
		output += "\n" + getDate() + "\n" + getServer() + "\n";
		
		return output;
		
	}
	
	/**
	 * This method executes the Put-command. This means that the body is read from the BufferedReader
	 * and written to the specified file. It also sets the intern variable 'success' to true if the file
	 * could be written.
	 */
	@Override
	public void execute() throws IOException{
		success = false; //to indicate whether the file upload was successful.
		body = "";
		String input;
		while(!(input = inFromClient.readLine()).equals("")){
			body += input + "\n";
		}
		try{
			PrintWriter out = new PrintWriter(clientSentence[1]);
			out.println(body);
			out.close();
			success = true;
		}catch(Exception e){
			success = false;
		}
		outToClient.writeBytes(getResponse());
	}
}
