package initLine;

import java.io.*;

/**
 * This class represents the get-command. It inherits from the head-class.
 * 
 * @author Jakob
 *
 */
public class Get extends Head {
	
	/**
	 * Constructor of the get-command. It's similar to the head-constructor.
	 * 
	 * @param clientSentence
	 * @param outToClient
	 */
	public Get(String[] clientSentence, DataOutputStream outToClient){
		super(clientSentence, outToClient);
	}

	/**
	 * This method returns a string with the information expected from a get-command.
	 * 
	 * @return	A string containing the header with information about the file as in 
	 * 			the head-class. It also contains a string representation of the needed file
	 * 			or a '404 Not Found' message if the file doesn't exist.
	 */
	public String getResponse(){
		String output = super.getResponse() + "\r\n";
		try{
			String fileName = clientSentence[1];
			if(fileName.contains("/"))
				fileName = fileName.substring(1);
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			 String line = null;
			 while ((line = br.readLine()) != null) {
			   output += line + "\r\n";
			 }
			 br.close();
		}catch(Exception e){
			output += "<html><head></head><body>404 Not Found</body></html>";
		}
		 return output;
	}
	
//	private void readFileToOutput() throws IOException{
//		try{
//			String fileName = clientSentence[1];
//			if(fileName.contains("/"))
//				fileName = fileName.substring(1);
//			File f = new File(fileName);
//			InputStream in = new FileInputStream(f);
//			int length = (int) f.length();
//			int read = 0;
//			byte[] bytes = new byte[length];
//			while ((read = in.read(bytes)) != -1 && length > 0) {
//				length = length-read; //Prevent other requests on inputstream to be read
//				outToClient.write(bytes, 0, read);
//			}
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			String error = "\r\n\r\n404 Not Found";
//			outToClient.writeBytes(error);
//		}
//	}
//	
//	@Override
//	public void execute() throws IOException{
//		super.execute();
//		readFileToOutput();
//	}
}
