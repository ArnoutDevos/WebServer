package Expressions;

public class Head {
	String path;
	String version;
	
	public Head(String path, String version) { //throws Exception !!!
		this.path = path;
		this.version = version;
	}

	public String getResponse(){
		return "HTTP/1.0 200 OK";
	}
}