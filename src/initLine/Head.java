package initLine;

public class Head implements Command{
	String arg;
	
	public Head(String arg) { //throws Exception !!!
		this.arg = arg;
	}

	@Override
	public String execute() {
		return "HTTP/1.0 200 OK";
		
	}
}