import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JonaClient {

	public static void main(String argv[]) throws Exception
	{
	while (true) { 
		BufferedReader inFromUser = new BufferedReader( new
				InputStreamReader(System.in));
		Socket clientSocket = new Socket(InetAddress.getByName("www.example.com"), 80);
		DataOutputStream outToServer = new DataOutputStream( clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		String t = "";
		while (!inFromServer.ready()) {
			outToServer.writeBytes((t = inFromUser.readLine()) + "\n");
			outToServer.flush();
			System.out.println("REQUEST WAS: " + t);
			}
		t="";
		while (inFromServer.ready()) { 
			t += inFromServer.readLine() + "\n";
			} 
		System.out.println(t);
		}
	}
}
