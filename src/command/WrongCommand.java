package command;

import java.io.*;

public class WrongCommand extends Command {

	String[] clientSentence;
	DataOutputStream outToClient;
	
	public WrongCommand(String[] clientSentence, DataOutputStream outToClient){
		this.clientSentence = clientSentence;
		this.outToClient = outToClient;
	}
	
	@Override
	public String getResponse(){
		String glued = "";
		for(int i = 0; i < clientSentence.length; i++)
			glued += clientSentence[i] + " ";
		return "500 Server Error \nWrong command! \n" + glued + "\n" + super.getResponse()+"\r\n\r\n<html><head></head><body>Wrong command.</body></html>";
	}
	
	public void execute() throws IOException{
		outToClient.writeBytes(getResponse());
	}
}
