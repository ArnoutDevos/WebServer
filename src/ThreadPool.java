import initLine.Handler;

import java.util.*;


public class ThreadPool {

	private LinkedList<Handler> handlerBucket;
	private ArrayList<Thread> threadPool;
	private int threadPoolSize;
	
	public ThreadPool(int threadPoolSize){
		this.threadPoolSize = threadPoolSize;
		threadPool = new ArrayList<>();
		handlerBucket = new LinkedList<>();
	}
	
	/**
	 * This method gives the handler to a thread if the threadPool is not full yet. Otherwise the
	 * handler will be placed in the handlerBucket which acts as a FIFO.
	 * @param handler
	 */
	public void execute(Handler handler){
		removeDead();
		fillWithWaiting();
		if(!full()){
			Thread thread = new Thread(handler);
			threadPool.add(thread);
			thread.start();
		} else {
			handlerBucket.add(handler);
		}
	}
	
	/**
	 * This method checks whether the threadPool is full. It also removes threads
	 * that don't work anymore.
	 * @return
	 */
	public boolean full(){
		if(threadPool.size() < threadPoolSize)
			return false;
		return true;
	}
	
	/**
	 * This method removes dead threads.
	 */
	public void removeDead(){
		Iterator<Thread> iterator = threadPool.iterator();
		while(iterator.hasNext())
			if(!iterator.next().isAlive())
				iterator.remove();
		
	}
	
	/**
	 * This method fills the threadPool whith waiting handlers from the handlerBucket.
	 */
	public void fillWithWaiting(){
		while(!full() && !handlerBucket.isEmpty()){
			Thread thread = new Thread(handlerBucket.removeFirst());
			threadPool.add(thread);
			thread.start();
		}
	}
}
