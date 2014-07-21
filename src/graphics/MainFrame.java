package graphics;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class MainFrame extends JFrame{
	
	/*
	 * This is the main window frame containing the panels and all UI elements in general
	 * 
	 * 
	 * 
	 */

	View view;
	MainPanel mainPanel;
	ControlPanel controlPanel;


	public MainFrame(View view) {

		this.view=view;
		
		setTitle("EngineV1");
		setLayout(null);
		//let's keep it that way for now
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//TO DO: FIX THE HARDCODING
		addMainPanel();

		addControlPanel();

		pack();
		setVisible(true);


	}

	private void addMainPanel() {
		mainPanel=new MainPanel(view);
		
		//Mainpanel size: 0-1550/0-800
		mainPanel.setBounds(0,0,view.getResolutionWidth()-50,view.getResolutionHeight()-200);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.red));

		add(mainPanel);
	}


	private void addControlPanel() {
		controlPanel=new ControlPanel(view,view.getDataBase());

		//Mainpanel size: 0-1600/800-1000
		controlPanel.setBounds(0,mainPanel.getHeight(),view.getResolutionWidth()-50,200);
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		controlPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		add(controlPanel);
	}



	/////////////////
	//	G E T T E R S
	/////////////////

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public ControlPanel getControlPanel() {
		return controlPanel;
	}
	


}
