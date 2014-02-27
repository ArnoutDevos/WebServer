package Expressions;

public class Get extends Head {

	public Get(String[] tokens){
		super(tokens);
	}
	
	@Override
	public String getResponse(){
		return "HTTP/1.0 201 OK";
	}
}
