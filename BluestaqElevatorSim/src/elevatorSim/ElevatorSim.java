package elevatorSim;

public class ElevatorSim 
{


	private static int agFloors = 0;
	private static int bsFloors = 0;
	private static Activity aLevel = Activity.NOT;
	private static StartWindow startWindow;
	private static Simulator sim;
	//Start up
	public static void main(String[] args)
	{
		ElevatorSim.startWindow = new StartWindow();
	}

	public static void setAGFloors(int agFloors)
	{
		ElevatorSim.agFloors = agFloors;
	}
	
	public static void setBSFloors(int bsFloors)
	{
		ElevatorSim.bsFloors = bsFloors;
	}

	public static void setALevel(Activity aLevel)
	{
		ElevatorSim.aLevel = aLevel;
	}
	
	public static int getAGFloors()
	{
		return ElevatorSim.agFloors;
	}
	
	public static int getBSFloors()
	{
		return ElevatorSim.bsFloors;
	}

	public static Activity getALevel()
	{
		return ElevatorSim.aLevel;
	}
	
	public static void startSimulation()
	{
		ElevatorSim.sim = new Simulator(agFloors, bsFloors, aLevel);
	}
}

