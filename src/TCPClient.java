import java.io.*;
import java.net.*;
class TCPClient
{
	public static void main(String argv[]) throws Exception
	{
		BufferedReader inFromUser = new BufferedReader( new
				InputStreamReader(System.in));
		while(true){
			Socket clientSocket = new Socket("localhost", 6789);
			DataOutputStream outToServer = new DataOutputStream
					(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new
					InputStreamReader(clientSocket.getInputStream()));

			String sentence = inFromUser.readLine();
			outToServer.writeBytes(sentence + '\n');
			String modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);
			clientSocket.close();
			if(sentence.equals("exit"))
				break;
		}
		
	}
}