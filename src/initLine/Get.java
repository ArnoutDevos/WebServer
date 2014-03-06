package initLine;

import java.io.*;

public class Get extends Head {
	
	
	public Get(String[] clientSentence, DataOutputStream outToClient){
		super(clientSentence, outToClient);
	}
	
	@Override
	public String getResponse(){
		String output = super.getResponse() + "\n";
		try{
		BufferedReader br = new BufferedReader(new FileReader(clientSentence[1]));
		 String line = null;
		 while ((line = br.readLine()) != null) {
		   output += "\n" + line;
		 }
		}catch(Exception e){
			output += "\n File not found.";
		}
		return output;
	}
}
