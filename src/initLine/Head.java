package initLine;
import java.io.*;



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
		if(fileName.contains("/"))
			fileName = fileName.substring(1);
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
			output += "200 OK";
			output += "\n" + "Content-type: " + extension;
			output += "\n" + "Content-length: " + f.length();
		} else {
			output += "404 Not Found";
		}
		
		output += "\n" + super.getResponse();
		return output;
		
	}
	
	@Override
	public void execute() throws IOException{
		outToClient.writeBytes(getResponse());
	}
}