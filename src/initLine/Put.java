package initLine;

import java.io.*;

import javax.activation.MimetypesFileTypeMap;

public class Put extends Head {

	private BufferedReader inFromClient;
	private String body;
	
	public Put(String[] clientSentence, DataOutputStream outToClient, BufferedReader inFromClient){
		super(clientSentence, outToClient);
		this.inFromClient = inFromClient;
	}
	
	public String getResponse(boolean succes) {
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
		boolean succes = false; //to indicate whether the file upload was succesful.
		body = "";
		String input;
		while(!(input = inFromClient.readLine()).equals("")){
			body += input + "\n";
		}
		try{
			PrintWriter out = new PrintWriter(clientSentence[1]);
			out.println(input);
			out.close();
		}catch(Exception e){
			
		}
	}
}
