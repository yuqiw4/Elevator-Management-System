// Melody Wang 59907761
// Tianran Zhang 37914655

public class SimClock {
	private static int simTime;
	
	// Constructor
	public SimClock(){
		simTime = 0;
	}
	
	public static void tick(){
		simTime++;
	}
	
	public static int getTime(){
		return simTime;
	}

}
