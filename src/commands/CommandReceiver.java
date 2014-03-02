package commands;

import java.util.Vector;
//TODO This should become the Handler class !!!
//Singleton pattern class
final class CommandReceiver {
  private Vector<?> c;
  private CommandArgument a;
     private CommandReceiver(){
       c = new Vector<String>();
     }
     private static CommandReceiver receiver = new CommandReceiver();
     public static CommandReceiver getReference() {
     return receiver;
     }
     public void setCommandArgument(CommandArgument a) {
     this.a = a;
     }
     public void sendStatus(){
    	 System.out.println("Status");
      }
     public void sendHeader(){
    	 System.out.println("Header");
     }
	public void sendBody() {
		System.out.println("Body");
		
	}
}
