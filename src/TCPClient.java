import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/* Command Examples
 * GET /lenna.html HTTP/1.1
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
