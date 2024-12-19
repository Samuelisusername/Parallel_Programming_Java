package Database;

public class MySemaphore {

	private volatile int count;

	public MySemaphore(int maxCount) {
		this.count = maxCount;
		
	}

	public void acquire() throws InterruptedException {
		synchronized(this) {
		while(count==0) {
			this.wait();
		}
		this.count++;
		}

	}

	public synchronized void release() {
		synchronized(this) {
			this.count--;
			this.notifyAll();
		}
	}

}
