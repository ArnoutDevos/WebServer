package initLine;

import java.io.*;

public class Get extends Head {
	
	
	public Get(String[] clientSentence, DataOutputStream outToClient){
		super(clientSentence, outToClient);
	}
	
	@Override
	public String getResponse(){
		String output = super.getResponse() + "\r\n";
		try{
			String fileName = clientSentence[1];
			if(fileName.contains("/"))
				fileName = fileName.substring(1);
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			 String line = null;
			 while ((line = br.readLine()) != null) {
			   output += line + "\n";
			 }
		}catch(Exception e){
			output += "404 Not Found";
		}
		return output + "\n";
	}
}
