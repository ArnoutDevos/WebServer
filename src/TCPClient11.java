import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/** 
 * This client uses either the HTTP 1.0 or 1.1 standard to communicate with a server over a socket connection.
 * The commands implemented are HEAD, GET, PUT and POST.
 * Requests for embedded objects will be pipelined as a demonstration of the pipelining capacities of the server
 * as well as the client. Persistent connections are established if HTTP 1.1 is used, connect and immediateley close otherwise.
 * 
 * Example of commando's:
 * GET /example.html HTTP/1.1
 * Host: localhost:6789
 * 
 * GET /lenna.html HTTP/1.1
 * Host: www.arnoutdevos.net
 * 
 * @author Arnout Devos
 * @version 1.0
 */
class TCPClient11
{
	/**
	 * Main method to call to initiate connection with the server.
	 * @param argv syntax: "host port" e.g. "localhost 6789"
	 * @throws Exception
	 */
	public static void main(String argv[]) throws Exception
	{
//		String adress = "www.arnoutdevos.net";
//		int port = 80;
		
		String adress = "localhost";
		int port = 6789;

		if(argv.length == 2){
			adress = argv[0];
			port = Integer.parseInt(argv[1]);
		}
		else
		{
			System.out.println("Wrong input arg. Now using default adress and port: "+adress+":"+port);
		}
		LinkedHashMap<String, Connection> connections = new LinkedHashMap<String, Connection>();
		BufferedReader inFromUser = new BufferedReader( new
				InputStreamReader(System.in));
		Socket clientSocket = new Socket(InetAddress.getByName(adress), port);
		InputStream in = clientSocket.getInputStream();
		OutputStream out = clientSocket.getOutputStream();

		Connection clientCon = new Connection(clientSocket, in, out, null);
		boolean persistent = false;
		DataOutputStream outToServer = new DataOutputStream
				(out);
		BufferedReader inFromServer = new BufferedReader(new
				InputStreamReader(in));
		while(true){
			
			String t;
			int i = 0;
			while(!inFromServer.ready()){
				
					t = inFromUser.readLine();
				outToServer.writeBytes((t) + "\r\n");
				outToServer.flush();
				if(t.length()-8 > 0 && i==0){
					if(t.substring(t.length()-8).equals("HTTP/1.1"))
						persistent = true;
					else
						persistent = false;
				}
				if(t.equals(""))
					Thread.sleep(1000);
				System.out.println("REQUEST WAS: " + t);
				i++;//counter to know which line is command and which line is header
				
			}
			System.out.println("Using persistent mode:"+persistent);
			//Start Pipeline test
			//			String u = "GET / HTTP/1.1\r\n"+"Host: " + adress + "\r\n\r\n";
			//			outToServer.writeBytes(u);//Pipeline test
			//			System.out.println("PIPE REQUEST WAS: " + u);
			//Stop Pipeline test
			//t = "";
			System.out.println("Retrieving file.");
			StringBuilder sBuffer = new StringBuilder();
			while(inFromServer.ready()){
				sBuffer.append(inFromServer.readLine() + "\n");
			}
			System.out.println(sBuffer);

			if(!persistent){
				break;
			}
			Document doc = Jsoup.parse(sBuffer.toString());
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

				String filename = extractFileName(url.getPath());
				File file = new File("C:\\Client\\"+filename);

				Socket clientSocketEmbedded;
				DataOutputStream outToServerEmbedded;
				Connection conEmbedded;

				if(connections.containsKey(temp)){
					conEmbedded = connections.get(temp);
				}
				else if(temp.equals("localhost")){
					conEmbedded = new Connection(clientCon.getSocket(), clientCon.getInput(), clientCon.getOutput(), file);
				}
				else{
					System.out.println(temp);
					clientSocketEmbedded = new Socket(InetAddress.getByName(temp), 80); //no connection before with this server
					conEmbedded = new Connection(clientSocketEmbedded, clientSocketEmbedded.getInputStream(), clientSocketEmbedded.getOutputStream(), file);
				}
				connections.put(temp, conEmbedded);

				outToServerEmbedded = new DataOutputStream
						(conEmbedded.getOutput());

				String request = "GET " + url.getPath() + " HTTP/1.1\r\n"+"Host: " + temp + "\r\n\r\n";
				System.out.println("Request: "+request);
				outToServerEmbedded.writeBytes(request);
				outToServer.flush();
				//java.util.concurrent.TimeUnit.SECONDS.sleep(1);//Disable the pipeline
				lookForData(connections);
			}
			System.out.println("All requests sent. Waiting for piped responses");
			while(lookForData(connections));
			if(!persistent){
				clientSocket.close();
			}
			System.out.println("All responses received. Please put in new requests!");
		}
	}
	/**
	 * @param connections a collection of the type LinkedHashMap with the currently established connections indexed by their domain name
	 * @return true if data has been retrieved from one of the connections, false if no connections serve data anymore.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static boolean lookForData(
			LinkedHashMap<String, Connection> connections) throws IOException, FileNotFoundException {
		Boolean result = true;
		InputStream inputStream;
		OutputStream outputStream;
		File file;
		if(!connections.isEmpty()){
			Iterator<Entry<String, Connection>> itr = connections.entrySet().iterator();
			Connection con = itr.next().getValue();
			file = con.getFile();

			BufferedReader possibleReader = new BufferedReader(new InputStreamReader(con.getInput()));
			if(possibleReader.ready()){
				inputStream = con.getInput();
				outputStream = new FileOutputStream(file.getAbsoluteFile());

				writeFromTo(inputStream, outputStream);

				if(!possibleReader.ready()){
					connections.remove(con);
				}
				itr.remove();
				System.out.println("Done!");
				System.out.println("File "+extractFileName(file.getPath())+" written: "+file.exists());
			}
		}
		else{
			result = false;
		}
		return result;
	}
	/**
	 * This method writes from a given InputStream to a given OutputStream which is in most cases a file.
	 * @param in InputStream to read the data from.
	 * @param out OutputStream to write the data to.
	 */
	private static void writeFromTo(InputStream in, OutputStream out){
		int read = 0;
		int offset = 0;
		String decoded = null;
		byte[] bytes = new byte[30000]; //buffer for header
		try {
			while ((read = in.read(bytes,offset,1)) != -1) {
				offset++;
				decoded = new String(bytes, "UTF-8");
				if(decoded.contains("\r\n\r\n")) {
					//System.out.println("Decoded is: "+decoded);//DEBUG
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			while ((read = in.read(bytes)) != -1 && length > 0) {
				length = length-read; //Prevent other requests on inputstream to be read
				out.write(bytes, 0, read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method returns a filename contained in a string. Mostly used to extract the exact filename from a full path URI.
	 * @param path The path out of which the filename needs to be extracted.
	 * @return The filename of the file e.g. /path/to/file/file.ext --> file.ext
	 */
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
