package initLine;

import java.io.BufferedReader;
import java.io.DataOutputStream;

public class Post extends Put {

	public Post(String[] clientSentence, DataOutputStream outToClient, BufferedReader inFromClient){
		super(clientSentence,outToClient,inFromClient);
	}
	
	@Override
	public String getResponse() {
		String output = "HTTP/1.0 ";
		if(succes){
			output += "200 Ok";
		} else
			output += "500 Server Error";
		output += "\n" + getDate() + "\n" + getServer() + "\n";
		
		return output;
		
	}
	
}
