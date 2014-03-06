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
				System.out.println("Sourcetje: " + temp);
				URL url = new URL(temp);
				temp = url.getHost();
				System.out.println("sitetje = " + temp);
				Socket clientSocketEmbedded = new Socket(InetAddress.getByName(temp), 80);
				DataOutputStream outToServerEmbedded = new DataOutputStream
						(clientSocketEmbedded.getOutputStream());
				BufferedReader inFromServerEmbedded = new BufferedReader(new
						InputStreamReader(clientSocketEmbedded.getInputStream()));
//				File mediaFile = new File("C:\\");
//				FileOutputStream fileStream = new FileOutputStream(mediaFile);
				String filename = extractFileName(url.getPath());
				File file = new File("C:\\Client\\"+filename);
//				if (!file.exists()) {
//					file.createNewFile();
//				}
				String request = "GET " + url.getPath() + " HTTP/1.1\r\n"+"Host: " + temp + "\r\n\r\n";
				System.out.println("Request: "+request);
				outToServerEmbedded.writeBytes(request);
				outToServer.flush();
				
				//FileWriter fw = new FileWriter(file.getAbsoluteFile());
				//BufferedWriter bw = new BufferedWriter(fw);
		        
				
				InputStream inputStream = null;
				OutputStream outputStream = null;
				
				//String result = "";
				//Boolean realData = false;
//				int length = 0;
//				while(!inFromServerEmbedded.ready())
//				while(!((temp = inFromServerEmbedded.readLine()) == null) || inFromServerEmbedded.ready()){//!((temp = inFromServerEmbedded.readLine()) == null) && !(temp.isEmpty()) ||
//					result += temp;
//					if(temp.toLowerCase().startsWith("content-length: ")){
//						length = Integer.parseInt(temp.toLowerCase().replace("content-length: ",""));
//					}
//					if(temp.equals("")) break;
//				}
				
				inputStream = clientSocketEmbedded.getInputStream();
				 
				// write the inputStream to a FileOutputStream
				outputStream = new FileOutputStream(file.getAbsoluteFile());
		 
				int read = 0;
				byte[] bytes = new byte[30000];
		 
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
		 
				System.out.println("Done!");
				
				//Boolean realData = false;
//				int length = 0;
//				while(!inFromServerEmbedded.ready())
//				while(!((temp = inFromServerEmbedded.readLine()) == null) || inFromServerEmbedded.ready()){//!((temp = inFromServerEmbedded.readLine()) == null) && !(temp.isEmpty()) ||
//					//temp = inFromServerEmbedded.readLine();
//					result += temp;
//					if(temp.toLowerCase().startsWith("content-length: ")){
//						length = Integer.parseInt(temp.toLowerCase().replace("content-length: ",""));
//					}
//					if(temp.equals("")) break;
//				}
//				byte[] dummy = temp.getBytes();
//				int offset = dummy.length;
//				int header = dummy.length;
				
				
				//BufferedInputStream in = new BufferedInputStream(clientSocketEmbedded.getInputStream());
				//InputStream in = clientSocketEmbedded.getInputStream();
	            //FileOutputStream fout = new FileOutputStream(file.getAbsolutePath());

//	            byte[] bytes = new byte[length];
//	            dummy = new byte[length];
//			      int numRead = 0;
//			      while (offset < bytes.length && !((numRead=in.read(dummy, 0, 10)) < 0)){
//			          offset += numRead;
//			      }
//			      
//			      if (offset < bytes.length) {
//			          throw new IOException("Could not completely read file " + file.getName() + ", offset = " + offset + ", bytes.length = " + bytes.length+", header length = "+header);
//			      }
			      
//	            byte data[] = new byte[1024];
//	            int count;
//	            while ((count = in.read(data, 0, 1024)) != -1)
//	            {
//	                fout.write(data, 0, count);
//	            }
//	            fout.close();
				
				
				//bos.flush();
				 //bw.close();
				 //System.out.println(result);
				System.out.println("File "+filename+" written: "+file.exists());
				clientSocketEmbedded.close();
	        }
			
			
			}
		}
	 private static String extractFileName(String path) {

		    if ( path == null)
		    {
		      return null;
		    }
		    String newpath = path.replace('\\','/');
		    int start = newpath.lastIndexOf("/");
		    if ( start == -1)
		    {
		      start = 0;
		    }
		    else
		    {
		      start = start + 1;
		    }
		    String pageName = newpath.substring(start, newpath.length());

		    return pageName;
		  }
		
	}
