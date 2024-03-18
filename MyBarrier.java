package Database;

public class MyBarrier {
	private int enoughNr; //number of people in the barrier
	private int currnum; //number of people currently waiting
	private boolean draining; //if newcomers have arrived too late
	
	
	MyBarrier(int n){
		//TODO find suitable variables for the barrier and initialize them
		this.enoughNr = n; 
		this.draining = false;
		this.currnum = 0;

    }

	synchronized void await() throws InterruptedException {
		while (draining) {
			wait(); // you are too late
		}
		currnum++; // yey officially registered in the line
		while(currnum < enoughNr && !draining) {
			wait(); // waiting for the last one to activate the draining
		}
		
		if(currnum-- == enoughNr) {
			
			draining = true;
			notifyAll();
		}
		if(currnum == 0) {
			draining = false; 
			notifyAll();
		}
		
		//TODO implement the barrier await using Monitors
		
		//nope becasue spurious wakeup
//		if(++currnum < enoughNr) {
//			wait();
//		}
//		else {
//			currnum = 0; 
//			notifyAll();
//		}
	}
}