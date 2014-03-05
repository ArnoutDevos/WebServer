import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/* Command Examples
 * GET / HTTP/1.1
 * Host: www.arnoutdevos.net
 */
class TCPClient
{
	public static void main(String argv[]) throws Exception
	{
		while(true){
			BufferedReader inFromUser = new BufferedReader( new
					InputStreamReader(System.in));
			Socket clientSocket = new Socket(InetAddress.getByName("www.arnoutdevos.net"), 80);
			DataOutputStream outToServer = new DataOutputStream
					(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new
					InputStreamReader(clientSocket.getInputStream()));
			String t;
			
			while(!inFromServer.ready()){
				outToServer.writeBytes((t = inFromUser.readLine()) + "\n");
				outToServer.flush();
//				System.out.println("REQUEST WAS: " + t);
			}
			t = "";
			while(inFromServer.ready()){
				t += inFromServer.readLine() + "\n";
			}
//			Document doc = Jsoup.parse(t);
//			Elements media = doc.select("[src]");
//			for (Element src : media) {
//				String temp = src.absUrl("src");
//				String tempArray[] = temp.split("/");
//				System.out.println("sitetje = " + tempArray[0]);
//				Socket clientSocketEmbedded = new Socket(InetAddress.getByName(tempArray[0]), 80);
//				DataOutputStream outToServerEmbedded = new DataOutputStream
//						(clientSocketEmbedded.getOutputStream());
//				BufferedReader inFromServerEmbedded = new BufferedReader(new
//						InputStreamReader(clientSocketEmbedded.getInputStream()));
//				outToServerEmbedded.writeBytes("GET " + src.attr("src") + " HTTP 1.1\n");
//				outToServerEmbedded.writeBytes("Host: " + src.attr("src") + " HTTP 1.1\n");
//				String result = "";
//				while(!(temp = inFromServerEmbedded.readLine()).isEmpty()){
//					result += temp + "\n";
//				}
//				System.out.println(result);
//				clientSocketEmbedded.close();
//	        }
			System.out.println(t);
			clientSocket.close();
			}
		}
		
	}
