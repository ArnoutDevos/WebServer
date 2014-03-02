package commands;
import java.util.*;
 class TransferCommand implements Command {
	  protected Handler receiver;
	  private Vector<?> cnamelist,carglist; 
	  private String cname;
	  private CommandArgument carg;
	  private Command command;
	  public TransferCommand () {
	    this(null,null);
	  }
	  public TransferCommand (Vector<?> cnamelist, Vector<?>
	carglist){
	    this.cnamelist = cnamelist;
	    this.carglist = carglist; 
	  }
	  public void execute(){
	    for (int i = 0; i < cnamelist.size(); i++) {
	      cname = (String)(cnamelist.get(i));
	      carg = (CommandArgument)((carglist.get(i)));
	      receiver.setCommandArgument(carg);
	      String classname = "commands." + cname + "Command";
	         try {
	           Class<?> cls = Class.forName(classname);
	           command = (Command) cls.newInstance();
	           command.setRunner(receiver);
	         }
	         catch (Exception e) {   
	                  e.printStackTrace();
	         }
	      command.execute();
	    } 
	  }
	@Override
	public void setRunner(Handler handler) {
		this.receiver = handler;	
	}
	}