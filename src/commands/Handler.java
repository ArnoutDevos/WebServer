package commands;
import java.io.*;
import java.net.*;
import java.util.Vector;


import Expressions.*;

public class Handler implements Runnable
{
	Socket connectionSocket;
	private CommandArgument arg;
	private  Vector<Object> clist,alist;
	private Reader inFromClient;
	private DataOutput outToClient;
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

	private void processIO(Reader inFromClient,
			DataOutput outToClient){
		//try {
			//Read the next line that the client submits
//			String clientSentence = inFromClient.readLine();
//			Head expr = makeExpression(clientSentence);
//			System.out.println("Received: " + clientSentence);
//			outToClient.writeBytes(expr.getResponse() + '\n');
			
			//New code!
			CommandArgument ca,ca2;
		     //TransferCommandTest t = new TransferCommandTest();
		     ca = new CommandArgument();
		     ca.setArgument("/index.html","HTTP/1.0");
		     //Vector<Object> myclist = t.getClist();
		     //Vector<Object> myalist = t.getAlist();
		     clist.addElement("Get"); alist.addElement(ca);
		     TransferCommand tc = new TransferCommand(clist,alist);
		     tc.setRunner(this);
		     CommandManager cm = new CommandManager(tc);       
		                    cm.runCommands();
		     this.clearBuffer(clist,alist);
		     ca2 = new CommandArgument();
		     ca2.setArgument("/register.html","HTTP/1.0");
		     clist.addElement("Post"); alist.addElement(ca2);
		     clist.addElement("Get"); alist.addElement(ca2);
		     TransferCommand tc2 = new TransferCommand(clist,alist);
		     tc2.setRunner(this);
		     CommandManager cm2 = new CommandManager(tc2);       
		                    cm2.runCommands();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
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