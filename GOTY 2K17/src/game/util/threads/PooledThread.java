package game.util.threads;

public class PooledThread extends Thread{
	private static IDAssigner threadId = new IDAssigner(1);
	private ThreadPool pool;
	public PooledThread(ThreadPool pool){
		super(pool,"PooledThread"+threadId.next());
		this.pool = pool;
	}
	@Override
	public void run(){
		Runnable task = null;
		while(!isInterrupted()){	
			task = pool.getTask();
		}
		if(task == null){ return;}
		try{
			task.run();
		}catch(Throwable t){
			pool.uncaughtException(this, t);
		}
	}
	
}
