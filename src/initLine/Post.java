package initLine;

import java.io.DataOutputStream;

public class Post extends Head {

	public Post(String[] clientSentence, DataOutputStream outToClient){
		super(clientSentence,outToClient);
	}
	
	@Override
	public String getResponse(){
		return "HTTP/1.0 203 OK";
	}
	
	public void execute(){
		
	}
}
