package command;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

	
/**
 * This class defines the commands in the server application. Every other command has to inherit from this class
 * so they must implement the method execute and can override the getResponse()-method if more information needs to be delivered.
 * 
 * @author Jakob
 *
 */
public abstract class Command {
	
	/**
	 * This method will execute the command e.g. put the right message in the outputstream.
	 * 
	 * @throws IOException
	 */
    public abstract void execute() throws IOException;
    
    /**
     * This method generates an appropriate string to put on the outputstream. It returns the date and
     * the name of the server. In can be overridden in the subclasses to add more information.
     * 
     * @return	Returns a string with the correct header and body.
     */
    public String getResponse(){
    	return getDate() + "\r\n" + getServer() + "\r\n";
    }
    
    /**
     * This method returns a string of the current date.
     * 
     * @return	String of the current date.
     */
    public String getDate(){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date now = new Date();
        String strDate = "Date: " + sdfDate.format(now) + " GMT";
        return strDate;
    }
    
    /**
     * This method returns the server-name.
     * 
     * @return String containing the server-name.
     */
    public String getServer(){
    	return "Server: My Server";
    }
}
