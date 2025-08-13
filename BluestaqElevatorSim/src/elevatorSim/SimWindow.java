package elevatorSim;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SimWindow extends JFrame
{
	private static final Dimension PANEL_SIZE = new Dimension(300, 500);
	private static final Dimension BUTTON_SIZE = new Dimension(55, 55);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int agFloorCount = 0;
	private int bsFloorCount = 0;
	private Simulator simulator;
	private ArrayList<JButton> floorButtons = new ArrayList<JButton>();
	private JPanel logPanel;
	private JPanel buttonPanel;
	private final int BUTTON_WINDOW_COLLUMNS = 4;
	private JTextArea log;
	//constructor
	public SimWindow(int agFloors, int bsFloors, Simulator sim)
	{
		super("Elevator Simulator");
		this.agFloorCount = agFloors;
		this.bsFloorCount = bsFloors;
		this.simulator = sim;
		createButtonList();
		

		createLogPanel();
		createButtonPanel();
		
		this.getContentPane().add(logPanel, BorderLayout.WEST);
		this.getContentPane().add(buttonPanel, BorderLayout.EAST);
		
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void createButtonList() {
		for(int i = agFloorCount; i > 0; i--)
		{
			JButton floorButton = new JButton(Integer.toString(i));
			floorButton.addActionListener(simulator);
			floorButton.setPreferredSize(BUTTON_SIZE);
			floorButton.setFont(new Font("Ariel", Font.PLAIN, 10));
			floorButtons.add(floorButton);
		}
		
		for(int j = 1; j <= bsFloorCount; j++)
		{
			JButton floorButton = new JButton("B" + Integer.toString(j));
			floorButton.addActionListener(simulator);
			floorButton.setPreferredSize(BUTTON_SIZE);
			floorButton.setFont(new Font("Ariel", Font.PLAIN, 10));
			floorButtons.add(floorButton);
		}
		
	}
	
	//make the button pane
	
	private void createButtonPanel()
	{
		ArrayList<ArrayList<JButton>> buttonArray = new ArrayList<>();
		for(int i = 0; i < floorButtons.size(); i = i+BUTTON_WINDOW_COLLUMNS)
		{
			int j = i + BUTTON_WINDOW_COLLUMNS;
			if(j <= floorButtons.size())
			{
				buttonArray.add( new ArrayList<JButton>(floorButtons.subList(i, j)));
			}
			else
			{
				buttonArray.add( new ArrayList<JButton>(floorButtons.subList(i, floorButtons.size())));
			}
		}
		
		buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder(new EtchedBorder(), "Floors"));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		for(int i = 0; i < buttonArray.size(); i++)
		{
			JPanel subButtons = new JPanel();
			for(int j = 0; j < buttonArray.get(i).size(); j++)
			{
				subButtons.add(buttonArray.get(i).get(j));
			}
			buttons.add(subButtons);
		}
		JScrollPane scroll = new JScrollPane(buttons);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(PANEL_SIZE);
		buttonPanel.add(scroll);
		buttonPanel.setPreferredSize(PANEL_SIZE);
		
	}
	
	//make the log panel
	private void createLogPanel()
	{
		logPanel = new JPanel();
		logPanel.setBorder(new TitledBorder(new EtchedBorder(), "Sim Log"));
		
		log = new JTextArea();
		log.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(log);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(PANEL_SIZE);
		
		logPanel.add(scroll);
		logPanel.setPreferredSize(PANEL_SIZE);
		
	}
	
	//Ensures that the buttons aren't pressable during proccessing time
	public boolean lockButtons(boolean trueUnlockFalseUnlock)
	{
		for(int i = 0; i < floorButtons.size(); i++)
		{
			floorButtons.get(i).setEnabled(trueUnlockFalseUnlock);
		}
		return true;
	}
	
	public void appendToLog(String logUpdate)
	{
		log.append(logUpdate);
		log.append("\n");
	}
}
