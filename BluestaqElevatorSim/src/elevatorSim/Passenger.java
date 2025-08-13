package elevatorSim;

public class Passenger 
{
	private long destinationFloor = 0;
	private long arrivalFloor = 0;
	
	public Passenger(long destination, long isAt)
	{
		this.setDestinationFloor(destination);
		this.setCurrentFloor(isAt);
	}

	public long getDestinationFloor() {
		return destinationFloor;
	}

	public void setDestinationFloor(long destinationFloor) {
		this.destinationFloor = destinationFloor;
	}

	public long getCurrentFloor() {
		return arrivalFloor;
	}

	public void setCurrentFloor(long currentFloor) {
		this.arrivalFloor = currentFloor;
	}

}
