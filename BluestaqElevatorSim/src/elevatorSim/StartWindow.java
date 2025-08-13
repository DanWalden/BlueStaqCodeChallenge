package elevatorSim;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

public class StartWindow extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int positiveFloors = 0;
	private int negativeFloors = 0;
	private Activity howBusy = Activity.NOT;
	private String floorString = "How many above ground floors: ";
	private String basementString = "How many basement floors: ";
	private String activityString = "How busy is this elevator: ";
	private static NumberFormat intFormat;
	private static NumberFormatter intFormatter;
	private JFormattedTextField aboveGroundFloors;
	private JFormattedTextField basementFloors;
	private JComboBox<Activity> dropdown;
	private JLabel floorLabel;
	private JLabel basementLabel;
	private JLabel activityLabel;
	private JButton okayButton;
	
	public StartWindow()
	{
		super("Initialize Elevator Sim");
		//Initialized the components of this window
		intFormat = NumberFormat.getIntegerInstance();
		
		intFormatter = new NumberFormatter(intFormat);
		intFormatter.setValueClass(Integer.class);
		intFormatter.setAllowsInvalid(false);
		
		aboveGroundFloors = new JFormattedTextField(intFormatter);
		basementFloors = new JFormattedTextField(intFormatter);
		aboveGroundFloors.setPreferredSize(new Dimension(100, 15));
		basementFloors.setPreferredSize(new Dimension(100, 15));
		dropdown = new JComboBox<Activity>(Activity.values());
		
		floorLabel = new JLabel(floorString);
		basementLabel = new JLabel(basementString);
		activityLabel = new JLabel(activityString);
		
		okayButton = new JButton("Start");
		okayButton.addActionListener(this);
		
		//Given More Time I would make the start window look less shitty
		JPanel aboveGroundPanel = new JPanel();
		aboveGroundPanel.add(floorLabel, BorderLayout.WEST);
		aboveGroundPanel.add(aboveGroundFloors, BorderLayout.EAST);
		this.getContentPane().add(aboveGroundPanel, BorderLayout.WEST);

		JPanel basementPanel = new JPanel();
		basementPanel.add(basementLabel, BorderLayout.WEST);
		basementPanel.add(basementFloors, BorderLayout.EAST);
		this.getContentPane().add(basementPanel, BorderLayout.CENTER);
		
		JPanel dropdownPannel = new JPanel();
		dropdownPannel.add(activityLabel, BorderLayout.WEST);
		dropdownPannel.add(dropdown, BorderLayout.EAST);
		this.getContentPane().add(dropdownPannel, BorderLayout.EAST);
		
		this.getContentPane().add(okayButton, BorderLayout.SOUTH);
		
		//Finalize Setup and display
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//Safety check
		if(e.getSource().equals(okayButton))
		{
			//send all user input to the actual simulator
			positiveFloors = ((Number)aboveGroundFloors.getValue()).intValue();
			ElevatorSim.setAGFloors(positiveFloors);
			
			negativeFloors = ((Number)basementFloors.getValue()).intValue();
			ElevatorSim.setBSFloors(negativeFloors);
			
			howBusy = dropdown.getItemAt(dropdown.getSelectedIndex());
			ElevatorSim.setALevel(howBusy);
			
			ElevatorSim.startSimulation();
			
			this.dispose();
			
		}
	}
	
}
