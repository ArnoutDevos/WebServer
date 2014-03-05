package initLine;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;


public class Head extends Command{
	
	protected String[] clientSentence;
	protected DataOutputStream outToClient;
	
	public Head(String[] clientSentence, DataOutputStream outToClient) { //throws Exception !!!
		this.clientSentence = clientSentence;
		this.outToClient = outToClient;
	}

	@Override
	public String getResponse() {
		String output = "HTTP/1.0 ";
		String fileName = clientSentence[1];
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
			output += "200 OK";
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			output += "\n" + "Content-type: " + mimeTypesMap.getContentType(f);
			output += "\n" + "Content-length: " + f.length();
		} else {
			output += "500 Server Error (File not found)";
		}
		
		output += "\n" + super.getResponse();
		return output;
		
	}
	
	@Override
	public void execute() throws IOException{
		outToClient.writeBytes(getResponse());
	}
}