package commands;
import initLine.*;

import java.io.*;
import java.net.*;
import java.util.Vector;



public class Handler implements Runnable
{
	Socket connectionSocket;
	private CommandArgument arg;
	private  Vector<Object> clist,alist;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	public Handler(Socket socket)
	{ 
		this.connectionSocket = socket;
		clist = new Vector<Object>(); 
	    alist = new Vector<Object>();
	}
	
	@Override
	public void run()
	{
		try{
		//Read the http request from the client into a buffered reader
		this.inFromClient = new BufferedReader(new
				InputStreamReader (connectionSocket.getInputStream()));
		//Create datastream from server to client
		this.outToClient = new DataOutputStream
				(connectionSocket.getOutputStream());
		//Let another method do the input/output processing
		processIO(inFromClient, outToClient);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}

	private void processIO(BufferedReader inFromClient,
			DataOutput outToClient){
		try {
			//Read the next line that the client submits
			String clientSentence = inFromClient.readLine();
//			Head expr = makeExpression(clientSentence);
			outToClient.writeBytes("Received: " + clientSentence);
//			outToClient.writeBytes(expr.getResponse() + '\n');
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clearBuffer(Vector<Object> c, Vector<Object> a) {
	     clist.removeAll(c);
	     alist.removeAll(a); 
	   }

	private Head makeExpression(String clientSentence) {
		//String[] tokens = clientSentence.split(" ");
		//String command = tokens[0].toUpperCase();
		String tmp = clientSentence;
		tmp = tmp.toUpperCase();
		if(tmp.startsWith("HEAD"))
			return new Head(clientSentence);
		if(tmp.startsWith("GET"))
			return new Get(clientSentence);
		if(tmp.startsWith("PUT"))
			return new Put(clientSentence);
		if(tmp.startsWith("POST"))
			return new Post(clientSentence);
		return new WrongCommand(clientSentence);
	}
	
	public void setCommandArgument(CommandArgument arg) {
	     this.arg = arg;
	 }
	public void sendStatus(){
   	 	try {
			outToClient.writeBytes("status");
			outToClient.writeBytes("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
     }
    public void sendHeader(){
    	try {
			outToClient.writeBytes("Header");
			outToClient.writeBytes("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public void sendBody() {
		try {
			outToClient.writeBytes("Body");
			outToClient.writeBytes("\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}