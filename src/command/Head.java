package command;
import java.io.*;


/**
 * This class represents the head-command in HTTP. It inherits from the command class and 
 * adds certain features to it.
 * 
 * @author Jakob
 *
 */
public class Head extends Command{
	
	/**
	 * Variable containing the information send by the client.
	 */
	protected String[] clientSentence;
	/**
	 * Variable containing the DataOutputStream to send data to.
	 */
	protected DataOutputStream outToClient;
	
	/**
	 * Constructor for a Head-command.
	 * 
	 * @param clientSentence
	 * 			String, containing the command given by the client
	 * @param outToClient
	 * 			DataOutputStream on which this head-command will write.
	 */
	public Head(String[] clientSentence, DataOutputStream outToClient) { //throws Exception !!!
		this.clientSentence = clientSentence;
		this.outToClient = outToClient;
	}

	/**
	 * This method returns a String with all the information expected from a head-command.
	 * 
	 * @return	Returns information about the HTTP-type, if the command could be executed,
	 * 			the content-type and the content-length of the file.
	 */
	@Override
	public String getResponse() {
		String output = clientSentence[2] + " ";
		String fileName = clientSentence[1];
		if(fileName.contains("/"))
			fileName = fileName.substring(1);
		File f = new File(fileName);
		if(f.exists() && !f.isDirectory()) { 
			output += "200 OK";
			output += "\r\n" + "Content-type: " + makeExtension(fileName);
			output += "\r\n" + "Content-length: " + f.length();
//			output += "\n" + "Cache-Control: max-age=86400, public";
		} else {
			output += "404 Not Found";
			output += "\r\n" + "Content-type: text/html";
		}
		
		output += "\r\n" + super.getResponse();
		
		return output;
		
	}
	
	@Override
	public void execute() throws IOException{
		outToClient.write(getResponse().getBytes());
		outToClient.flush();
	}
	
	/**
	 * This method evaluates the extension of a certain fileName.
	 * 
	 * @param fileName
	 * 			FileName of which the extension is needed.
	 * @return	A string containing the extension of the given file.
	 */
	public String makeExtension(String fileName){
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		if(extension.equals("html"))
			extension = "text/html";
		else if(extension.equals("jpg") || extension.equals("jpeg"))
			extension = "image/jpeg";
		else if(extension.equals("png"))
			extension = "image/png";
		return extension;
	}
}