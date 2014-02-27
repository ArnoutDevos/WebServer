package Expressions;

public class Head {
	String[] arg;
	
	public Head(String[] arg) { //throws Exception !!!
		this.arg = arg;
	}

	public String getResponse(){
		return "HTTP/1.0 200 OK";
	}
}