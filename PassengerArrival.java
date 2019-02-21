// Melody Wang 59907761
// Tianran Zhang 37914655

public class PassengerArrival {
	private int numPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	
	//constructor
	public PassengerArrival(int numPassengers, int destinationFloor, int timePeriod, int expectedTimeOfArrival){
		this.numPassengers = numPassengers;
		this.destinationFloor = destinationFloor;
		this.timePeriod = timePeriod;
		this.expectedTimeOfArrival = expectedTimeOfArrival;
		
	}
	
	//getters
	public int getNumPassengers(){
		return numPassengers;
	}
	
	public int getDestinationFloor(){
		return destinationFloor;
	}

	public int getExpectedTimeOfArrival(){
		return expectedTimeOfArrival;
	}
	
	
	//setters
	public void resetExpectedTimeOfArrival(int currentExpectedTimeOfArrival){
		expectedTimeOfArrival = currentExpectedTimeOfArrival + timePeriod;
	}
	
	
}
