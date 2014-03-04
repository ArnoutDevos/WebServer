package initLine;

public class Put extends Head {

	public Put(String tokens){
		super(tokens);
	}
	
	@Override
	public String execute(){
		return "HTTP/1.0 202 OK";
	}
}
