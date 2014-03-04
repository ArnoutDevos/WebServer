package initLine;

public class WrongCommand implements Command {

	String clientSentence;
	
	public WrongCommand(String clientSentence){
		this.clientSentence = clientSentence;
	}
	
	@Override
	public String execute(){
		return "Wrong command: " + this.clientSentence;
	}
}
