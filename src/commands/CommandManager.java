package commands;

 class CommandManager {
	   private Command myCommand;
	   public CommandManager(Command  myCommand) {
	     this.myCommand  =  myCommand ;    
	   }
	   public void runCommands( ) {
	            myCommand.execute();     
	   }
	 }