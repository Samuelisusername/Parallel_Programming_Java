package Database;

public class MySemaphore {

	private volatile int count;

	public MySemaphore(int maxCount) {
		//TODO initialize count suitably
		this.count = maxCount;
		
	}

	public void acquire() throws InterruptedException {
		synchronized(this) {
		while(count==0) {
			this.wait();
		}
		this.count++;
		}
		//TODO implment suitable monitor and implement semaphore acquisition
	}

	public synchronized void release() {
		//TODO implment suitable monitor and implement semaphore release
		synchronized(this) {
			this.count--;
			this.notifyAll();
		}
	}

}
