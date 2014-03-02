package commands;
//The Command interface
public interface Command {
    public abstract void execute();
	void setRunner(Handler handler);
}