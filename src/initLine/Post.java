package initLine;

public class Post extends Head {

	public Post(String tokens){
		super(tokens);
	}
	
	@Override
	public String execute(){
		return "HTTP/1.0 203 OK";
	}
}
