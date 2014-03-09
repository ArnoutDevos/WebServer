import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Connection {

	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private File file;
	
	public Connection(Socket socket, InputStream input, OutputStream output, File file){
		this.socket = socket;
		this.input = input;
		this.output = output;
		this.file = file;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public InputStream getInput() {
		return input;
	}
	
	public OutputStream getOutput() {
		return output;
	}
	
	public File getFile() {
		return file;
	}
}
