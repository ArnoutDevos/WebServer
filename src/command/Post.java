package command;

import java.io.*;

/**
 * This class represents a Post-command.
 * @author Jakob
 *
 */
public class Post extends Put {

	public Post(String[] clientSentence, DataOutputStream outToClient, BufferedReader inFromClient){
		super(clientSentence,outToClient,inFromClient);
	}
	
	@Override
	public String getResponse() {
		String output = clientSentence[2] + " ";
		if(success){
			output += "200 Ok";
		} else
			output += "500 Server Error";
		output += "\n" + getDate() + "\n" + getServer() + "\n";
		
		return output;
		
	}
	
	@Override
	public void execute() throws IOException{
		success = false; //to indicate whether the file upload was succesful.
		body = "";
		String input;
		body = "Posted at: " + getDate() + "\n";
		while(!(input = inFromClient.readLine()).equals("")){
			body += input + "\n";
		}
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(clientSentence[1], true)));
		    out.println(body);
		    out.close();
		    success = true;
		} catch (IOException e) {
			success = false;
		}
		
		outToClient.writeBytes(getResponse());
	}
	
}
