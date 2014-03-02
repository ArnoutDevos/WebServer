package commands;

public class GetCommand extends TransferCommand{
	//private Handler receiver;
	public GetCommand(){
	}
	@Override
	public void execute(){
		receiver.sendStatus();
		receiver.sendHeader();
		receiver.sendBody();
	}
}
