package Expressions;

public class Put extends Head {

	public Put(String[] tokens){
		super(tokens);
	}
	
	@Override
	public String getResponse(){
		return "HTTP/1.0 202 OK";
	}
}
