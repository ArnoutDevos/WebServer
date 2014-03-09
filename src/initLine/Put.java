package initLine;

import java.io.*;

public class Put extends Head {

	protected BufferedReader inFromClient;
	protected String body;
	protected boolean succes;
	
	public Put(String[] clientSentence, DataOutputStream outToClient, BufferedReader inFromClient){
		super(clientSentence, outToClient);
		this.inFromClient = inFromClient;
	}
	
	@Override
	public String getResponse() {
		String output = clientSentence[2] + " ";
		if(succes){
			output += "201 Created";
		} else
			output += "500 Server Error";
		output += "\n" + getDate() + "\n" + getServer() + "\n";
		
		return output;
		
	}
	
	@Override
	public void execute() throws IOException{
		succes = false; //to indicate whether the file upload was succesful.
		body = "";
		String input;
		while(!(input = inFromClient.readLine()).equals("")){
			body += input + "\n";
		}
		try{
			PrintWriter out = new PrintWriter(clientSentence[1]);
			out.println(body);
			out.close();
			succes = true;
		}catch(Exception e){
			succes = false;
		}
		outToClient.writeBytes(getResponse());
	}
}
