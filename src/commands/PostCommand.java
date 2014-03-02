package commands;

 class PostCommand extends TransferCommand{
	//private Handler receiver;
	public PostCommand(){
	}
	@Override
	public void execute(){
		receiver.sendStatus();
		receiver.sendHeader();
		//receiver.sendBody(); //only commented out to see the difference with GET
	}
}
