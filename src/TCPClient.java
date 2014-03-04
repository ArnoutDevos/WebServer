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
				
				while(inFromServer.ready()){
					t = inFromServer.readLine();
					System.out.println(t);
				}
				break;
			}
//			String inputLine;
//	        StringBuilder request = new StringBuilder();
//				while(!(inputLine = inFromUser.readLine()).isEmpty()){
//					request.append(inputLine);
//				}
			
			String request = inFromUser.readLine();
			System.out.println("REQUEST WAS: " + request);
			
			outToServer.writeBytes(request + "\r\n");
			outToServer.flush();
//			if(inFromServer.ready()){
//				//clientSocket.close();
//				String t = inFromServer.readLine();
//				System.out.println(t);
//				break;
//			}
			
//			String t = inFromServer.readLine();
//			System.out.println(t);
			
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
		clientSocket.close();
//		String t;
//		while((t = inFromServer.readLine()) != null) System.out.println(t);
	}
}