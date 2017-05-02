package game.util.threads;

import java.nio.channels.IllegalSelectorException;
import java.util.LinkedList;
import java.util.List;
import game.util.*;

public class ThreadPool extends ThreadGroup{

	private boolean alive;
	private List<Runnable> taskQueue;
	private static IDAssigner poolId = new IDAssigner(1);
	
	public ThreadPool(int threads) {
		super("threadPool"+ poolId.next());
		setDaemon(true);
		taskQueue = new LinkedList<Runnable>();
		alive = true;
		for(int i=0;i<threads;i++){
			new PooledThread(this).start();
		}
	}
	
	public synchronized void runTask(Runnable task){
		if(!alive) throw new IllegalSelectorException();
		if(task != null){
			taskQueue.add(task);
			notify(); //tells thread to start task
		}
	}
	
	public synchronized void close(){
		if(!alive) return;
		alive = false;
		taskQueue = null; 
		interrupt();
	}
	
	public void join(){
		synchronized(this){
		alive = false; 
		notifyAll();
		}
		Thread[] threads = new Thread[activeCount()];
		int count = enumerate(threads);
		for(int i=0;i<count;i++){
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
	}
	
	public synchronized Runnable getTask(){
		while(taskQueue.size() == 0){
			if(!alive) return null;
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return taskQueue.remove(0);
		
	}
}
