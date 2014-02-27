package Expressions;

public class WrongCommand extends Head {

	public WrongCommand(String[] tokens){
		super(tokens);
	}
	
	@Override
	public String getResponse(){
		return "Wrong command.";
	}
}
