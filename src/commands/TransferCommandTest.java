package commands;
import java.util.*;
public class TransferCommandTest {
	   private  Vector<Object> clist,alist; 
	   public TransferCommandTest() {
	     clist = new Vector<Object>(); 
	       alist = new Vector<Object>();
	   }
	   public void clearBuffer(Vector<Object> c, Vector<Object> a) {
	     clist.removeAll(c);
	       alist.removeAll(a); 
	   }
	   public Vector<Object> getClist() {
	     return clist;
	   }
	   public Vector<Object> getAlist() {
	     return alist;
	   }
	    public static void main(String[] args) {
	       CommandArgument ca,ca2;
	     TransferCommandTest t = new TransferCommandTest();
	     ca = new CommandArgument();
	     ca.setArgument("/index.html","HTTP/1.0");
	     Vector<Object> myclist = t.getClist();
	     Vector<Object> myalist = t.getAlist();
	     myclist.addElement("Get"); myalist.addElement(ca);
	     TransferCommand tc = new TransferCommand(myclist,myalist);
	     CommandManager cm = new CommandManager(tc);       
	                    cm.runCommands();
	     t.clearBuffer(myclist,myalist);
	     ca2 = new CommandArgument();
	     ca2.setArgument("/register.html","HTTP/1.0");
	     myclist = t.getClist();
	     myalist = t.getAlist();
	     myclist.addElement("Post"); myalist.addElement(ca2);
	     myclist.addElement("Get"); myalist.addElement(ca2);
	     TransferCommand tc2 = new TransferCommand(myclist,myalist);        
	     CommandManager cm2 = new CommandManager(tc2);       
	                    cm2.runCommands();
	   }
	 }