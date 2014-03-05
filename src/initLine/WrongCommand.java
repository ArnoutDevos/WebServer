package initLine;

public class WrongCommand extends Command {

	String[] clientSentence;
	
	public WrongCommand(String[] clientSentence){
		this.clientSentence = clientSentence;
	}
	
	@Override
	public String execute(){
		return "Wrong command! \n" + super.execute();
	}
}
