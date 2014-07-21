package graphics;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	
	/*
	 * This is the bottom panel, containing various UI elements
	 * 
	 * Listeners get added 'after the fact' because of racism
	 * 
	 */
	
	database.EngineDatabase dataBase;
	helper.UIController uiController;
	View view;
	
	JButton start = new JButton("Start");
	JButton quit = new JButton("Quit");
	JButton pause = new JButton("Pause");
	JTextField colliderCount = new JTextField("100");
	
	
	public ControlPanel(View view,database.EngineDatabase dataBase){
		this.view=view;
		add(start);
		add(pause);
		add(quit);
		add(colliderCount);
		
		repaint();
	}

	private void buildControls() {
		start.addActionListener(uiController);
	
		pause.addActionListener(uiController);
		
		quit.addActionListener(uiController);
	}
	
	public void addUIController(helper.UIController uiController){
		this.uiController=uiController;
		
		buildControls();
		
	}

	
	public int getColliderCount() {
		return Integer.valueOf(colliderCount.getText());
	}

}
