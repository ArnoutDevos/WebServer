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
	
	
	public int[] getData(){
		int[] data;
		try{
			String fileName = clientSentence[1];
			if(fileName.contains("/"))
				fileName = fileName.substring(1);
			data = readFile(fileName);
		}catch(Exception e){
			data = new int[3];
			data[0] = 4;
			data[1] = 0;
			data[2] = 4;
		}
		return data;
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
	
	
	public int[] readFile(String fileName)
			throws FileNotFoundException, IOException {
		int[] data;
		//option 1 (not good)
//		output = FileUtils.readFileToString(new File(fileName));
		//option 2 (not good)
//		BufferedReader br = new BufferedReader(new FileReader(fileName));
//		 String line = null;
//		 while ((line = br.readLine()) != null) {
//		   output += line + "\n";
//		 }
		//option 3 (crap)
//		BufferedReader br = new BufferedReader(new FileReader(fileName));
//		 int line = 0;
//		 while ((line = br.read()) != -1) {
//		   output += line;
//		 }
		//option 4
		FileInputStream fis = new FileInputStream(new File(fileName));
		int size = fis.available();
		data = new int[size];
		for(int i = 0; i < size; i++) {
			   data[i] = fis.read();
			 }
		fis.close();
		return data;
	}
	
	@Override
	public void execute() throws IOException{
		outToClient.writeBytes(getResponse());
//		int[] data = getData();
//		outToClient.writeBytes(super.getResponse());
//		for(int i : data){
//			outToClient.writeByte(i);
//		}
	}
}
