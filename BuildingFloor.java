// Melody Wang 59907761
// Tianran Zhang 37914655

public class BuildingFloor {

	//local variable
	private int[] totalDestinationRequests;
	int[] arrivedPassengers;
	int[] passengerRequests;
	int approachingElevator;
	
	//constructor
	public BuildingFloor(){
		this.totalDestinationRequests = new int[5];
		this.arrivedPassengers = new int[5];
		this.passengerRequests = new int[5];
		this.approachingElevator = -1;
	}
	
	//setter
	public void updateTotalDestinationRequests(int floor, int passengers){
		this.totalDestinationRequests[floor] += passengers;
	}
	
	public void setArrivedPassengers(int floor, int passengers){
		this.arrivedPassengers[floor] += passengers;
	}
	
	public void setPassengerRequests(int floor, int passengers){
		this.passengerRequests[floor] = passengers;
	}
	
	public void setApproachingElevator(int elevatorID){
		this.approachingElevator = elevatorID;
	}
	
	//getter
	
	public int[] getTotalDestinationRequests(){
		return totalDestinationRequests;
	}
	
	public int[] getArrivedPassengers(){
		return arrivedPassengers;
	}
	
	public int[] getPassengerRequests(){
		return passengerRequests;
	}
	
	public int getApproachingElevator(){
		return approachingElevator;
	}

	
	//update the floor info when there are new PassengerArrival
	//used in the BuildingManager
	public void setPassengerArrival(int numPassengers, int dest) {
		updateTotalDestinationRequests(dest,  numPassengers);
		setPassengerRequests(dest, numPassengers);
		
	}
	
	//sum up the total number of passengers requesting elevator access on this floor
	public int sumOfTotalRequests(){
		int sum = 0;
		for (int i = 0; i < 5; i++){
			sum += this.getTotalDestinationRequests()[i];
		}
		return sum;
	}
	
	//sum up the total number of passengers that exited an elevator on the floor
	public int sumOfArrival(){
		int sum = 0;
		for (int i = 0; i < 5; i++){
			sum += this.getArrivedPassengers()[i];
		}
		return sum;
	}
	
	//sum up the total number of passengers currently requesting elevator access on this floor
	public int sumOfRequests(){
		int sum = 0;
		for (int i = 0; i < 5; i++){
			sum += this.getPassengerRequests()[i];
		}
		return sum;
	}

	
	
}






