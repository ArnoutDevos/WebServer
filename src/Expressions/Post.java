package Expressions;

public class Post extends Head {

	public Post(String tokens){
		super(tokens);
	}
	
	@Override
	public String getResponse(){
		return "HTTP/1.0 203 OK";
	}
}
