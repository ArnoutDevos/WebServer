import java.io.*;
import java.net.*;
/* Command Examples
 * GET /lenna.html HTTP/1.1
 * Host: www.arnoutdevos.net
 */
class TCPClient
{
	public static void main(String argv[]) throws Exception
	{
<<<<<<< HEAD
		BufferedReader inFromUser = new BufferedReader( new
				InputStreamReader(System.in));
		Socket clientSocket = new Socket(InetAddress.getByName("localhost"), 6789);
		DataOutputStream outToServer = new DataOutputStream
				(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new
				InputStreamReader(clientSocket.getInputStream()));
=======
>>>>>>> b2927367fef33fab11a7347a543f1cfcd5a67ca9
		while(true){
			BufferedReader inFromUser = new BufferedReader( new
					InputStreamReader(System.in));
			Socket clientSocket = new Socket(InetAddress.getByName("www.arnoutdevos.net"), 80);
			DataOutputStream outToServer = new DataOutputStream
					(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new
					InputStreamReader(clientSocket.getInputStream()));
			String t;
<<<<<<< HEAD
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
=======
>>>>>>> b2927367fef33fab11a7347a543f1cfcd5a67ca9
			
			while(!inFromServer.ready()){
				outToServer.writeBytes((t = inFromUser.readLine()) + "\n");
				outToServer.flush();
				System.out.println("REQUEST WAS: " + t);
			}
			t = "";
			while(inFromServer.ready()){
				t += inFromServer.readLine() + "\n";
			}
			System.out.println(t);
			Document doc = Jsoup.parse(t);
			Elements media = doc.select("[src]");
			System.out.println("Retrieving embedded elements.");
			for (Element src : media) {
				String temp = src.absUrl("src");
				System.out.println("Sourcetje: " + temp);
				URL url = new URL(temp);
				temp = url.getHost();
				System.out.println("sitetje = " + temp);
				Socket clientSocketEmbedded = new Socket(InetAddress.getByName(temp), 80);
				DataOutputStream outToServerEmbedded = new DataOutputStream
						(clientSocketEmbedded.getOutputStream());
				BufferedReader inFromServerEmbedded = new BufferedReader(new
						InputStreamReader(clientSocketEmbedded.getInputStream()));
				System.out.println("GET request: " + "GET " + src.attr("src") + " HTTP 1.1\r\n");
				outToServerEmbedded.writeBytes("GET " + src.attr("src") + " HTTP 1.1\r\n");
				outToServerEmbedded.writeBytes("Host: " + temp + " HTTP 1.1\r\n");
				outToServerEmbedded.writeBytes("\r\n");
				outToServer.flush();
				String result = "";
				while(!inFromServerEmbedded.ready())
				while(!((temp = inFromServerEmbedded.readLine()) == null) && !(temp.isEmpty())){
					result += temp + "\n";
				}
				System.out.println(result);
				clientSocketEmbedded.close();
	        }
			
			clientSocket.close();
			}
		}
		
	}
