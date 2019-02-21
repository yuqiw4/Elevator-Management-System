// Melody Wang 59907761
// Tianran Zhang 37914655

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorSimulation {

	//local variable
	private int currentSimulationTime;
	private int totalSimulationTime;
	private int simulatedSecondRate;
	private ArrayList<ArrayList<PassengerArrival>> arrivalEachFloor;
	
	//Building	components	such as	the	Elevator threads and the BuildingManager.
	static BuildingManager manager;
	private Elevator elevator0;
	private Elevator elevator1;
	private Elevator elevator2;
	private Elevator elevator3;
	private Elevator elevator4;
	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Thread t4;
	private Thread t5;
	
	
	//construction
	public ElevatorSimulation(){
		this.currentSimulationTime = 0;
		this.totalSimulationTime = 1000;
		this.simulatedSecondRate = 100;
		this.arrivalEachFloor = new ArrayList<ArrayList<PassengerArrival>>();
		
		ElevatorSimulation.manager = new BuildingManager();
		
		this.elevator0 = new Elevator(0, manager);
		this.elevator1 = new Elevator(1, manager);
		this.elevator2 = new Elevator(2, manager);
		this.elevator3 = new Elevator(3, manager);
		this.elevator4 = new Elevator(4, manager);
		
		this.t1 = new Thread(elevator0);
		this.t2 = new Thread(elevator1);
		this.t3 = new Thread(elevator2);
		this.t4 = new Thread(elevator3);
		this.t5 = new Thread(elevator4);
		
	}
	
	
	
	//public methods: .start()
	public void start() throws InterruptedException{
		
		//read ElevatorConfig.txt
		readFile();
		
		//start 5 threads
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		//keeping check if there are some people coming to a floor to request elevator
		while (SimClock.getTime() <= totalSimulationTime) {
			for (int i = 0; i < arrivalEachFloor.size(); i++){
				for (int j = 0; j < arrivalEachFloor.get(i).size(); j++){
					if (this.arrivalEachFloor.get(i).get(j).getExpectedTimeOfArrival()==SimClock.getTime()){
						//there are some people coming to a floor
						PassengerArrival arrival = arrivalEachFloor.get(i).get(j);
						manager.setArrival(i, arrival.getNumPassengers(), arrival.getDestinationFloor());
						this.arrivalEachFloor.get(i).get(j).resetExpectedTimeOfArrival(SimClock.getTime());
						
						String event = String.format("Time: %1$-3s || %2$-2spassengers enter the floor %3$-2sand request to go to the floor %4$-2s", currentSimulationTime, arrival.getNumPassengers(),i,arrival.getDestinationFloor());
						System.out.println(event);
					}
				}
			}
			
			Thread.sleep(simulatedSecondRate);
			SimClock.tick();
			currentSimulationTime++;
		}
		
	}



	//print building state
	public void printBuildingState(){
		System.out.println();
		System.out.println("Simulation State: ");
		System.out.println();
		// For each building floor
		System.out.println("---- State for each floor in the Building ----");
		for(int i = 0; i < 5; i++){
			System.out.println();
			System.out.println("  -- For the floor " + i + " -- ");
			System.out.println("   The total number of passengers requesting elevator access on the floor " + i +" : "+manager.getFloors()[i].sumOfTotalRequests());
			System.out.println("   The total number of passengers that exited an elevator on the floor" + i +" : "+ manager.getFloors()[i].sumOfArrival());
			System.out.println("   The current number of passengers waiting for an elevator on the floor " + i +" : "+manager.getFloors()[i].sumOfRequests());
			if (manager.getFloors()[i].getApproachingElevator()== -1){
				System.out.println("   No elevator is currently heading towards the floor " + i +" for passenger pickup");
			}else{
				System.out.println("   Elevator " + manager.getFloors()[i].getApproachingElevator() + " is currently heading towards the floor " + i +" for passenger pickup");
			}
		}
		System.out.println();
		
		// For each Elevator
		System.out.println("---- State for each elevator ----");
		System.out.println();
		System.out.println("  -- For the Elevator 0 -- ");
		System.out.println("   The total number of passengers that entered the Elevator 0 throughout the simulation : "+ elevator0.getTotalLoadPassengers());
		for(int i = 0; i < 5; i++){
			System.out.println("   The total number of passengers that exited the Elevator 0 on the floor " +i+ " : "+ elevator0.getTotalPassengerDestinations()[i]);
		}
		System.out.println("   The current number of passengers in the Elevator 0 : "+elevator0.getnumPassengers());
		
		System.out.println();
		System.out.println("  -- For the Elevator 1 -- ");
		System.out.println("   The total number of passengers that entered the Elevator 1 throughout the simulation : "+ elevator1.getTotalLoadPassengers());
		for(int i = 0; i < 5; i++){
			System.out.println("   The total number of passengers that exited the Elevator 1 on the floor " +i+ " : "+ elevator1.getTotalPassengerDestinations()[i]);
		}
		System.out.println("   The current number of passengers in the Elevator 1 : "+elevator1.getnumPassengers());
		
		System.out.println();
		System.out.println("  -- For the Elevator 2 -- ");
		System.out.println("   The total number of passengers that entered the Elevator 2 throughout the simulation : "+ elevator2.getTotalLoadPassengers());
		for(int i = 0; i < 5; i++){
			System.out.println("   The total number of passengers that exited the Elevator 2 on the floor " +i+ " : "+ elevator2.getTotalPassengerDestinations()[i]);
		}
		System.out.println("   The current number of passengers in the Elevator 2 : "+elevator2.getnumPassengers());
		
		System.out.println();
		System.out.println("  -- For the Elevator 3 -- ");
		System.out.println("   The total number of passengers that entered the Elevator 3 throughout the simulation : "+ elevator3.getTotalLoadPassengers());
		for(int i = 0; i < 5; i++){
			System.out.println("   The total number of passengers that exited the Elevator 3 on the floor " +i+ " : "+ elevator3.getTotalPassengerDestinations()[i]);
		}
		System.out.println("   The current number of passengers in the Elevator 3 : "+elevator3.getnumPassengers());
		
		System.out.println();
		System.out.println("  -- For the Elevator 4 -- ");
		System.out.println("   The total number of passengers that entered the Elevator 4 throughout the simulation : "+ elevator4.getTotalLoadPassengers());
		for(int i = 0; i < 5; i++){
			System.out.println("   The total number of passengers that exited the Elevator 4 on the floor " +i+ " : "+ elevator4.getTotalPassengerDestinations()[i]);
		}
		System.out.println("   The current number of passengers in the Elevator 4 : "+elevator4.getnumPassengers());
		
	}
	
	
	//public void readFile()
	public void readFile(){
		Scanner inFile;
		try{
			inFile = new Scanner(new File("ElevatorConfig.txt"));
			String line;
			String[] stringArray;
			String[] stringArray2;
			
			line = inFile.nextLine();
			totalSimulationTime = Integer.valueOf(line);
			line = inFile.nextLine();
			simulatedSecondRate = Integer.valueOf(line);
			
			while (inFile.hasNextLine()){
				ArrayList<PassengerArrival> arrival = new ArrayList<PassengerArrival>();
				
				line = inFile.nextLine();
				stringArray = line.split(";");
				for (int i = 0; i < stringArray.length; i++) {
					stringArray2 = stringArray[i].split(" ");
					int	numPassengers = Integer.valueOf(stringArray2[0]);
					int	destinationFloor = Integer.valueOf(stringArray2[1]);
					int	timePeriod = Integer.valueOf(stringArray2[2]);
					int	expectedTimeOfArrival = Integer.valueOf(stringArray2[2]);
					arrival.add(new PassengerArrival(numPassengers,destinationFloor,timePeriod,expectedTimeOfArrival));
					}
				this.arrivalEachFloor.add(arrival);
				
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} finally {
			System.out.println("Finished reading file");
		}
	}
	
}
