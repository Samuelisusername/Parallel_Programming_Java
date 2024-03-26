package Bridge;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeCondition extends Bridge {

	//TODO use this given lock and create conditions form it
	//you might find that you need some additional variables
	final Lock bridgeLock = new ReentrantLock();
	private int CarCounter =0;
	private int TruckCounter =0;
	private Condition CarcanGo = bridgeLock.newCondition();
	private Condition TruckcanGo = bridgeLock.newCondition();

	public void enterCar() throws InterruptedException {
		

			bridgeLock.lock();
			while (CarCounter >= 3 || TruckCounter >= 1) {
				CarcanGo.await();
			}
			CarCounter++;
			bridgeLock.unlock();
		//TODO implement rules for car entry
		
	}

	public void leaveCar() {
		bridgeLock.lock();
		if(CarCounter>=0) {
			CarCounter--;
			if(CarCounter==0) {
				TruckcanGo.signalAll();
			}
			CarcanGo.signalAll();
		}
		}
		//TODO implement rules for car leave

	public void enterTruck() throws InterruptedException {
		//TODO implement rules for truck entry - similar to car entry
		bridgeLock.lock();
		while(CarCounter>=1||TruckCounter>=1) {
			TruckcanGo.await();
		}
		TruckCounter++;
		bridgeLock.unlock();
		
	}

	public void leaveTruck() {
		//TODO implement rules for car leave - similar to car leave
		bridgeLock.lock();
		CarcanGo.signalAll();
		TruckcanGo.signalAll();
		TruckCounter++;
		bridgeLock.unlock();
	}

	public static void main(String[] args) {
		Random r = new Random();
		BridgeCondition b = new BridgeCondition();
		for (int i = 0; i < 100; ++i) {
			if (r.nextBoolean()) {
				(new Car()).driveTo(b);
			} else {
				(new Truck()).driveTo(b);
			}
		}
	}

}
