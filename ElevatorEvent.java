// Melody Wang 59907761
// Tianran Zhang 37914655

public class ElevatorEvent {

	private int destination;
	private int expectedArrival;
	
	//constructor
	
	public ElevatorEvent(int destination, int expectedArrival){
		this.destination = destination;
		this.expectedArrival = expectedArrival;
	}
	
	//getters
	public int getDestination(){
		return destination;
	}
	
	public int getExpectedArrival(){
		return expectedArrival;
	}
}
