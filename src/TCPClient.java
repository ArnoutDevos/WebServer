import java.io.*;
import java.net.*;
class TCPClient
{
	public static void main(String argv[]) throws Exception
	{
		//while(true){
//			BufferedReader inFromUser = new BufferedReader( new
//					InputStreamReader(System.in));
			Socket clientSocket = new Socket(InetAddress.getByName("localhost"), 6789);
			DataOutputStream outToServer = new DataOutputStream
					(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new
					InputStreamReader(clientSocket.getInputStream()));
//			String inputLine;
//	        StringBuilder request = new StringBuilder();
//				while((inputLine = inFromUser.readLine()) != null){
//					request.append(inputLine);
//				}
//				System.out.println("REQUEST WAS: " + request.toString());
//				
//			if(request.toString().toUpperCase().startsWith("EXIT")){
//				clientSocket.close();
//				break;
//			}
			
			//String sentence = inFromUser.readLine();
			outToServer.writeBytes("GET / HTTP/1.1");
			outToServer.writeBytes("\r\n");
			outToServer.writeBytes("Host: example.com");
			outToServer.writeBytes("\r\n");
			outToServer.writeBytes("\r\n");
			outToServer.flush();
			
			String t;
			while((t = inFromServer.readLine()) != null) System.out.println(t);
			inFromServer.close();
			clientSocket.close();
			//String modifiedSentence = inFromServer.readLine();
			
//			inputLine = null;
//	        StringBuilder response = new StringBuilder();
//				//while((inputLine = inFromServer.readLine()) != null){
//	        		response.append(inFromServer.readLine());//response.append(inputLine);
//				//}
//			System.out.println(response);
		//}
	}
}