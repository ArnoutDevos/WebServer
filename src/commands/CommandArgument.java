package commands;

 class CommandArgument {
	   private String[] args;
	   public CommandArgument() {
	     args = new String[2];
	   }
	   public String[] getArguments() {
	    return args;
	   }
	   public void setArgument(String s1, String s2) {
	         args[0] = s1;
	         args[1] = s2;
	   }
	 }