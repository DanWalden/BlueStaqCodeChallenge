package elevatorSim;

import java.util.ArrayList;

public class Elevator 
{
	public static final int PASSENGER_LIMIT = 10;
	private boolean goingUp = true;
	private long currentFloor = 0;
	private ArrayList<Passenger> passengerArray = new ArrayList<>();
	private long userDestination = 0;
	
	public Elevator()
	{
		
	}
	
	public boolean isGoingUp()
	{
		return this.goingUp;
	}
	
	public long getCurrentFloor()
	{
		return this.currentFloor;
	}
	
	public long getUserDestination()
	{
		return this.userDestination;
	}
	
	public void setGoingUp(boolean isGoingUp)
	{
		this.goingUp = isGoingUp;
	}
	
	//Move up and down 1 floor. there is no floor 0
	public long changeFloorOneStep()
	{
		if(this.goingUp)
		{
			this.currentFloor++;
		}
		else
		{
			this.currentFloor--;
		}
		if(currentFloor == 0)
		{
			if(this.goingUp)
			{
				this.currentFloor++;
			}
			else
			{
				this.currentFloor--;
			}
		}
		
		return this.currentFloor;
	}
	
	public void setCurrentFloor(long currentFloor)
	{
		this.currentFloor = currentFloor;
	}
	
	public void setUserDestination(long destination)
	{
		this.userDestination = destination;
	}
	
	public boolean addPassenger(Passenger newPassenger)
	{
		if(this.passengerArray.size() < 10)
		{
			passengerArray.add(newPassenger);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int checkDepatures()
	{
		int results = 0;
		for(int i = 0; i < this.passengerArray.size(); i++)
		{
			if(this.currentFloor == this.passengerArray.get(i).getDestinationFloor())
			{
				this.passengerArray.remove(i);
				results++;
			}
		}
		return results;
	}
 
}
