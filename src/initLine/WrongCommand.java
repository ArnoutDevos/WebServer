package initLine;

import java.io.DataOutputStream;
import java.io.IOException;

public class WrongCommand extends Command {

	String[] clientSentence;
	DataOutputStream outToClient;
	
	public WrongCommand(String[] clientSentence, DataOutputStream outToClient){
		this.clientSentence = clientSentence;
		this.outToClient = outToClient;
	}
	
	@Override
	public String getResponse(){
		return "Wrong command! \n" + super.getResponse();
	}
	
	public void execute() throws IOException{
		outToClient.writeBytes(getResponse());
	}
}
