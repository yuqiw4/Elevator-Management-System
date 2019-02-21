// Melody Wang 59907761
// Tianran Zhang 37914655

import java.util.ArrayList;

public class Elevator implements Runnable{
	
	//local variables
	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<ElevatorEvent> moveQueve;
	private int[] passengerDestinations;
	static BuildingManager manager;
	
	private int[] totalPassengerDestinations;
	private int pickupFloor;
	private int[] requests;
	
	// Constructor
	public Elevator(int elevatorID, BuildingManager manager){
		this.elevatorID = elevatorID;
		Elevator.manager = manager;
		this.currentFloor = 0;
		this.numPassengers = 0;
		this.totalLoadPassengers = 0;
		this.totalUnloadedPassengers = 0;
		this.moveQueve = new ArrayList<ElevatorEvent>();
		this.passengerDestinations = new int[5];
		this.pickupFloor = -1;
		this.requests = new int[5];
		this.totalPassengerDestinations = new int[5];
		
	}
	
	//.run() method
	public void run() {
		while (!Thread.interrupted()){
			
			//check if the elevator is idle
			if (moveQueve.size() == 0){
				//elevator is idle
				
				//check pickupFloor
				pickupFloor = manager.getPickupFloor(elevatorID);
				
				if (pickupFloor != -1){
					//there exists a floor with passenger requests
					
					//move to the pickup floor
					moveQueve.add(new ElevatorEvent(pickupFloor, SimClock.getTime() + Math.abs((currentFloor-pickupFloor)*5)));

					String currentTime = String.format("Time: %1$-3s || ", SimClock.getTime());
					System.out.println(currentTime +  "Elevator "+elevatorID+ " is heading to the floor "+ pickupFloor+" to pick up passengers");
					
				} else {
					//there does NOT exist a floor with passenger requests
					continue;
				}

			} else if (moveQueve.size() > 0){
				//elevator is not idle
				//process event
				if (moveQueve.get(0).getExpectedArrival() == SimClock.getTime() && this.numPassengers == 0){
					//Case1: pick up passengers
					currentFloor = moveQueve.get(0).getDestination();
					
					String currentTime = String.format("Time: %1$-3s || ", SimClock.getTime());
					System.out.println(currentTime +  "Elevator "+elevatorID+ " reaches to the floor "+ currentFloor + " to pick up passengers");

					for (int s = 0; s < 5; s++){
						this.requests[s] = manager.getFloors()[currentFloor].getPassengerRequests()[s];
					}

					boolean ifPassengersUp = false;

					//check whether exists passengers who want to go up
					for (int i = currentFloor; i < 5; i++){
						if(i > currentFloor && requests[i] != 0){
							if (this.moveQueve.size() == 1){
								moveQueve.add(new ElevatorEvent(i, SimClock.getTime() + 20 + Math.abs((currentFloor-i)*5)));
							} else {
								moveQueve.add(new ElevatorEvent(i, moveQueve.get(moveQueve.size()-1).getExpectedArrival() + 10 + Math.abs((moveQueve.get(moveQueve.size()-1).getDestination()-i)*5)));
							}
							ifPassengersUp = true;
							numPassengers += requests[i];
							totalLoadPassengers += requests[i];
							passengerDestinations[i] = 0;
							
							manager.resetPassengersRequests(currentFloor, i);
							//manager.getFloors()[currentFloor].updateTotalDestinationRequests(i, requests[i]);

						}
					}

					//there does NOT exist passengers who want to go up
					if (ifPassengersUp == false){
						for (int i = currentFloor; i >= 0; i--){
							if(i < currentFloor && requests[i] != 0){
								if (this.moveQueve.size() == 1){
									moveQueve.add(new ElevatorEvent(i, SimClock.getTime() + 20 + Math.abs((currentFloor-i)*5)));
								} else {
									moveQueve.add(new ElevatorEvent(i, moveQueve.get(moveQueve.size()-1).getExpectedArrival() + 10 + Math.abs((moveQueve.get(moveQueve.size()-1).getDestination()-i)*5)));
								}
								numPassengers += requests[i];
								totalLoadPassengers += requests[i];
								
								manager.resetPassengersRequests(this.currentFloor, i);
								//manager.getFloors()[currentFloor].updateTotalDestinationRequests(i, requests[i]);
							}
						}
					}
					
					for (int x = 1; x < this.moveQueve.size(); x++){
						System.out.println(" -- PICK UP -- Elevator ID: "+elevatorID+" -- Number of passengers: "+ requests[moveQueve.get(x).getDestination()] + " -- Destination floor: "+ moveQueve.get(x).getDestination()+" -- ");
						}
					
					currentTime = String.format("Time: %1$-3s || ", SimClock.getTime());
					System.out.println(currentTime +  "Elevator "+elevatorID+ " is heading to the floor "+ moveQueve.get(1).getDestination() + " to unload passengers");
					
					this.moveQueve.remove(0);
					manager.getFloors()[currentFloor].setApproachingElevator(-1);
					
				} else if (moveQueve.get(0).getExpectedArrival() == SimClock.getTime() && numPassengers > 0){
					//Case2: drop off passengers
					
					currentFloor = moveQueve.get(0).getDestination();
					numPassengers -= requests[currentFloor];
					totalUnloadedPassengers += requests[currentFloor];
					updateTotalPassengerDestinations(currentFloor, requests[currentFloor]);
					manager.getFloors()[currentFloor].setArrivedPassengers(pickupFloor, requests[currentFloor]);
					
					String currentTime = String.format("Time: %1$-3s || ", SimClock.getTime());
					System.out.println(currentTime +  "Elevator "+elevatorID+ " reaches to the floor "+ currentFloor + " to unload passengers");
					System.out.println(" -- DROP OFF -- Elevator ID: "+ elevatorID + " -- Current floor: " + currentFloor + " -- Number of passengers: " + requests[currentFloor]);
					
					requests[currentFloor] = 0;
					moveQueve.remove(0);
					
					if(moveQueve.size()>0){
						System.out.println(currentTime +  "Elevator "+elevatorID+ " is heading to the floor "+ moveQueve.get(0).getDestination() + " to unload passengers -- ");
					}
					
				}
			}
		}
	}

	public int getTotalLoadPassengers() {
		return totalLoadPassengers;
	}

	public int getTotalUnloadPassengers() {
		return totalUnloadedPassengers;
	}

	public int getnumPassengers() {
		return numPassengers;
	}
	
	public int[] getTotalPassengerDestinations(){
		return totalPassengerDestinations;
	}

	//keep track the total passenger destination for one specific floor
	public void updateTotalPassengerDestinations(int floor, int numPassenger){
		totalPassengerDestinations[floor] += numPassenger;
	}
}


