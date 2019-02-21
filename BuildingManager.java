// Melody Wang 59907761
// Tianran Zhang 37914655

public class BuildingManager {
	//local variable
	private BuildingFloor[] floors;

	//constructor
	public BuildingManager(){
		this.floors = new BuildingFloor[5];
		floors[0] = new BuildingFloor();
		floors[1] = new BuildingFloor();
		floors[2] = new BuildingFloor();
		floors[3] = new BuildingFloor();
		floors[4] = new BuildingFloor();
	}


	//getter
	public synchronized BuildingFloor[] getFloors(){
		return floors;
	}
	
	//setter
	public synchronized void resetPassengersRequests(int currentFloor, int resetFloor){
		floors[currentFloor].setPassengerRequests(resetFloor, 0);
		
	}

	//update the floor info when there are new PassengerArrival
	//used in the ElevatorSimulation
	public synchronized void setArrival(int currentFloor, int  numPassengers, int Dest){
		floors[currentFloor].setPassengerArrival(numPassengers, Dest);
	}
	
	//check whether there exists a floor with passenger requests
	//if there does, then return that floor
	//if there doesn't, then return -1
	public synchronized int getPickupFloor(int elevatorID) {
		int i;
		for (i=0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				if (floors[i].passengerRequests[j] != 0 && floors[i].getApproachingElevator() == -1){
					floors[i].approachingElevator = elevatorID;
					return i;
				}
			}
		}
		return -1;
		
	}
}
