package initLine;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

	
public abstract class Command {
	
	
    public abstract void execute() throws IOException;
    
    public String getResponse(){
    	return getDate() + "\n" + getServer() + "\n";
    }
    
    public String getDate(){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date now = new Date();
        String strDate = "Date: " + sdfDate.format(now) + " GMT";
        return strDate;
    }
    
    public String getServer(){
    	return "Server: My Server";
    }
}
