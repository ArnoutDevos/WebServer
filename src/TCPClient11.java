import java.io.*;
import java.net.*;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/* Command Examples
 * GET /lenna.html HTTP/1.1
 * Host: www.arnoutdevos.net
 */
class TCPClient11
{
	public static void main(String argv[]) throws Exception
	{
		String adress = "localhost";
		int port = 6789;
		
		if(argv.length == 2){
			adress = argv[0];
			adress = argv[1];
		}
		else
	    {
			System.out.println("Wrong input arg. Now using default adress and port: "+adress+":"+port);
	    }
		while(true){
			HashMap<String, Socket> connections = new HashMap<String, Socket>();
			BufferedReader inFromUser = new BufferedReader( new
					InputStreamReader(System.in));
			Socket clientSocket = new Socket(InetAddress.getByName(adress), port);
			
			connections.put(adress, clientSocket);
			
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
			System.out.println("Retrieving file.");
			while(inFromServer.ready()){
				t += inFromServer.readLine() + "\n";
			}
			System.out.println(t);
			clientSocket.close();
			
			Document doc = Jsoup.parse(t);
			Elements media = doc.select("[src]");
			System.out.println("Retrieving embedded elements.");
			for (Element src : media) {
				String temp = src.absUrl("src");
				String completeUrl=temp;
				if(temp.equals("")){
					src.setBaseUri("http://"+adress);
					completeUrl = src.absUrl("src");
				}
				System.out.println("Sourcetje: " + completeUrl);
				URL url = new URL(completeUrl);
				temp = url.getHost();
				System.out.println("sitetje = " + temp);
				Socket clientSocketEmbedded = new Socket(InetAddress.getByName(temp), 80);
				DataOutputStream outToServerEmbedded = new DataOutputStream
						(clientSocketEmbedded.getOutputStream());

				String filename = extractFileName(url.getPath());
				File file = new File("C:\\Client\\"+filename);

				String request = "GET " + url.getPath() + " HTTP/1.1\r\n"+"Host: " + temp + "\r\n\r\n";
				System.out.println("Request: "+request);
				outToServerEmbedded.writeBytes(request);
				outToServer.flush();
		        
				InputStream inputStream = null;
				OutputStream outputStream = null;
				
				inputStream = clientSocketEmbedded.getInputStream();
				 
				// write the inputStream to a FileOutputStream
				outputStream = new FileOutputStream(file.getAbsoluteFile());
		 
				 int read = 0;
				 int offset = 0;
				 String decoded = null;
					byte[] bytes = new byte[30000];
					while ((read = inputStream.read(bytes,offset,1)) != -1) {
						offset++;
						decoded = new String(bytes, "UTF-8");
						if(decoded.contains("\r\n\r\n")) {
							//System.out.println("Decoded is: "+decoded);//DEBUG
							break;
						}
					}
				String[] split = decoded.split("\r\n");
				String searchString = "content-length: ";
				int length = 0;
				for(String line : split){
					if(line.toLowerCase().contains(searchString)){
						length = Integer.parseInt(line.substring(line.indexOf(searchString) + searchString.length()+1));
						break;
					}
				}
				System.out.println("lengte is "+length);
				read = 0;
				bytes = new byte[length];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
				outputStream.close();
				inputStream.close();
				System.out.println("Done!");
				
				System.out.println("File "+filename+" written: "+file.exists());
				clientSocketEmbedded.close();
	        }
			
			
			}
		}
	 private static String extractFileName(String path) {
		    if ( path == null)
		      return null;
		    String newpath = path.replace('\\','/');
		    int start = newpath.lastIndexOf("/");
		    if ( start == -1) 
		    	start = 0; 
		    else 
		    	start++;
		    String pageName = newpath.substring(start, newpath.length());

		    return pageName;
		  }
		
	}
