package elevatorSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;

public class Simulator implements ActionListener
{
	private int agFloors = 0;
	private int bsFloors = 0;
	private double newPassengerChance = 0d;
	private SimWindow simWindow;
	private Elevator elevator = new Elevator();
	
	private ArrayList<Passenger> passengerArray = new ArrayList<>();
	
	public Simulator(int agFloors, int bsFloors, Activity aLevel)
	{
		this.agFloors = agFloors;
		this.bsFloors = bsFloors;
		
		switch(aLevel) 
		{
			case LOW:
				newPassengerChance = 0.05d;
				break;
			case MEDIUM:
				newPassengerChance = 0.1d;
				break;
			case HIGH:
				newPassengerChance = 0.2d;
				break;
			default:
				newPassengerChance = 0d;					
			
		}
		
		//create window
		simWindow = new SimWindow(agFloors, bsFloors, this);
		elevator.setCurrentFloor((long)1);
	}
	
	
	//Main Sim loop
	private void simLoop()//Could be optimized by reducing other loops, potentially by using dictionaries and passenger ids
	{
		String floor;
		long currentFloor = elevator.getCurrentFloor();
		long userDestination = elevator.getUserDestination();
		while(currentFloor != userDestination)
		{
			if(currentFloor < userDestination)
			{
				if(!elevator.isGoingUp())
				{
					elevator.setGoingUp(true);
					simWindow.appendToLog("Elevator is going up.");
				}
			}
			else
			{
				if(elevator.isGoingUp())
				{
					elevator.setGoingUp(false);
					simWindow.appendToLog("Elevator is going down.");
				}
			}
			
			currentFloor = elevator.changeFloorOneStep();
			
			checkForPassengerCalls();
			
			checkForPassengerArrival();
			
			int depatures = elevator.checkDepatures();
			if(depatures != 0)
			{
				floor = floorStringGenerator(currentFloor);
				simWindow.appendToLog( Integer.toString(depatures) + " Passengers left at floor " + floor );
			}
			
		}
		simWindow.lockButtons(true);

		floor = floorStringGenerator(currentFloor);
		simWindow.appendToLog("Arriving At Floor " + floor);
	}

	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof JButton)
		{
			JButton selectedFloor = (JButton) e.getSource();
			String label = selectedFloor.getText();
			if(label.charAt(0) != 'B')
			{
				elevator.setUserDestination(Long.valueOf(label));
			}
			else
			{
				elevator.setUserDestination(Long.valueOf(label.substring(1)) * -1);
			}
			simWindow.lockButtons(false);
			
			simLoop();
		}
	}
	
	
	
	//new passenger call check
	public void checkForPassengerCalls()
	{
		Random r = new Random();
		double passengerCheck = r.nextDouble(0.0d, 1.0d);
		if(newPassengerChance < 0.05d)
		{
			return;
		}
		else
		{
			if(passengerCheck <= newPassengerChance)
			{
				long passengerDestination = r.nextLong(bsFloors * -1, agFloors);
				long currentPassengerFloor = r.nextLong(bsFloors * -1, agFloors);
				if((passengerDestination != currentPassengerFloor) && (passengerDestination != 0 && currentPassengerFloor != 0))
				{
					if(passengerArray.size() < Elevator.PASSENGER_LIMIT)
					{
						passengerArray.add(new Passenger(passengerDestination, currentPassengerFloor));
						
						String floor = floorStringGenerator(currentPassengerFloor);
						
						simWindow.appendToLog("Passenger Calls for Elevator At Floor " + floor);
					}
				}	
			}
		}
	}


	private String floorStringGenerator(long currentFloor) 
	{
		String floor = (currentFloor < 0) ? "B": new String();
		floor += Long.toString(Math.abs(currentFloor));
		return floor;
	}


	private void checkForPassengerArrival() 
	{
		if(!passengerArray.isEmpty())
		{
			for(int i = 0; i < passengerArray.size(); i++)
			{	
				Passenger passenger = passengerArray.get(i);
				if(elevator.getCurrentFloor() == passenger.getCurrentFloor())
				{
					String floor = floorStringGenerator(passenger.getCurrentFloor());
					if(elevator.addPassenger(passenger))
					{
						passengerArray.remove(i); 
						i--;
						
						simWindow.appendToLog("New Passenger Enters The Elevator At Floor " + floor);
					}
					else
					{
						simWindow.appendToLog("New Passenger Tries To nters The Elevator At Floor " + floor + " But It Was Full.");
					}
				}
			}
		}
	}
	
}
