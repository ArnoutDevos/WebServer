import java.io.*;
import java.net.*;
class TCPClient
{
	public static void main(String argv[]) throws Exception
	{
		BufferedReader inFromUser = new BufferedReader( new
				InputStreamReader(System.in));
		Socket clientSocket = new Socket(InetAddress.getByName("www.example.com"), 80);
		DataOutputStream outToServer = new DataOutputStream
				(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new
				InputStreamReader(clientSocket.getInputStream()));
		while(true){
			String t;
			if(inFromServer.ready()){
				while(inFromServer.ready() && (t = inFromServer.readLine()) != null) System.out.println(t);
				break;
			}
			else{
//			String inputLine;
//	        StringBuilder request = new StringBuilder();
//				while(!(inputLine = inFromUser.readLine()).isEmpty()){
//					request.append(inputLine);
//				}
				
			String request = inFromUser.readLine();
			if(request.toUpperCase().equals("EXIT")) break;
			System.out.println("REQUEST WAS: " + request);
				
			outToServer.writeBytes(request + "\n");
			outToServer.flush();
			
			}
			
			//outToServer.flush();
			//String sentence = inFromUser.readLine();
//			outToServer.writeBytes("GET / HTTP/1.1");
//			outToServer.writeBytes("\r\n");
//			outToServer.writeBytes("Host: example.com");
//			outToServer.writeBytes("\r\n");
//			outToServer.writeBytes("\r\n");
//			outToServer.flush();
			
			//System.out.println("FROM SERVER:" + inFromServer.readLine());
			
			//inFromServer.close();
			//String modifiedSentence = inFromServer.readLine();
			
//			inputLine = null;
//	        StringBuilder response = new StringBuilder();
//				//while((inputLine = inFromServer.readLine()) != null){
//	        		response.append(inFromServer.readLine());//response.append(inputLine);
//				//}
//			System.out.println(response);
		}
		//String t;
		//while((t = inFromServer.readLine()) != null){
			//System.out.println(t);
		clientSocket.close();
		}
		
	}
