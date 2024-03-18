package Bridge;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeMonitor extends Bridge {
	// TODO use this object as a monitor
	// you might find that you need some additional variables.
	private final Object monitor = new Object();
	private int CarCounter = 0;
	private int TruckCounter = 0;

	public void enterCar() throws InterruptedException {
		//TODO implement rules for car entry
		synchronized(monitor) {
			while(CarCounter >=3||TruckCounter>=1) { //while the bridge is blocked
				//here we give up the monitor such that others can change the while condition
				monitor.wait();
				//here we wait for a notify and then try to aquire the monitor
			}
			//when the bridge is free and we got the bridge monitor
			//one more car (ours) can get onto the bridge
			CarCounter++;
			
		}
		
		
		
	}

	public void leaveCar() {
		CarCounter--;
		synchronized(monitor) {
		
		monitor.notifyAll();}
		//TODO implement rules for car leave
	}

	public void enterTruck() throws InterruptedException {
		//TODO implement rules for truck entry - similar to car entry
		//we want to enter a truck
		synchronized (monitor) { // lets get the bridge monitor
			while(CarCounter >=1||TruckCounter>=1) { // and check if the bridge is availible
				//incase it's not, we give the monitor away and hope for better days
				monitor.wait();
				//we only get to this part of the code when there is a notify (the notify should only happen when a car or truck leave) then we aquire the monitor and check again
			}
			TruckCounter++;
		}
	}

	public void leaveTruck() {
		//TODO implement rules for car leave - similar to car leave
		TruckCounter--;
		synchronized(monitor) {
			
			monitor.notifyAll();
		}
	}

	public static void main(String[] args) {
		Random r = new Random();
		BridgeMonitor b = new BridgeMonitor();
		for (int i = 0; i < 100; ++i) {
			if (r.nextBoolean()) {
				(new Car()).driveTo(b);
			} else {
				(new Truck()).driveTo(b);
			}
		}
	}

}
