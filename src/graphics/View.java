package graphics;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class View {

	/*
	 * Viewport main class
	 * 
	 * Keep references to UI stuff in here
	 * 
	 * 
	 */

	//main information
	database.EngineDatabase dataBase;
	int resolutionX,resolutionY;
	Dimension resolution;

	//associated containers
	MainFrame mainFrame;

	//associated containees
	MainPanel mainPanel;

	//popupmenu
	PopupMenu popup;

	//UI stuff
	UI ui;

	//marking markingBox
	Rectangle markingBox = new Rectangle();



	public View(int resolutionX, int resolutionY,
			database.EngineDatabase database){

		this.dataBase=database;

		//making it available in 2 formats right now
		this.resolutionX=resolutionX;this.resolutionY=resolutionY;
		resolution=new Dimension(resolutionX,resolutionY);


		mainFrame=new MainFrame(this);
		mainFrame.addWindowListener(new helper.MainWindowListener(dataBase));
		mainFrame.setLocation(20, 20);
		mainFrame.setSize(getResolution());


		mainPanel=mainFrame.getMainPanel();

		popup = new PopupMenu(dataBase.getController());
		mainPanel.add(popup);

		//create UI
		ui=new UI();

	}


	/////////////////////////
	/////	
	/////	M A R K I N G	B O X
	/////
	/////////////////////////

	public void createBox(){
		if(dataBase.getLastMouseClickPosition()[0]<dataBase.getLastMousePosition()[0]){
			if(dataBase.getLastMousePosition()[1]<dataBase.getLastMouseClickPosition()[1]){
				markingBox.setBounds(dataBase.getLastMouseClickPosition()[0],dataBase.getLastMousePosition()[1],(dataBase.getLastMousePosition()[0]-dataBase.getLastMouseClickPosition()[0]),(dataBase.getLastMouseClickPosition()[1]-dataBase.getLastMousePosition()[1]));
			}
			else{
				markingBox.setBounds(dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1],(dataBase.getLastMousePosition()[0]-dataBase.getLastMouseClickPosition()[0]),(dataBase.getLastMousePosition()[1]-dataBase.getLastMouseClickPosition()[1]));
			}
		}
		else{
			if(dataBase.getLastMousePosition()[1]<dataBase.getLastMouseClickPosition()[1]){
				markingBox.setBounds(dataBase.getLastMousePosition()[0],dataBase.getLastMousePosition()[1],(dataBase.getLastMouseClickPosition()[0]-dataBase.getLastMousePosition()[0]),(dataBase.getLastMouseClickPosition()[1]-dataBase.getLastMousePosition()[1]));
			}
			else{
				markingBox.setBounds(dataBase.getLastMousePosition()[0],dataBase.getLastMouseClickPosition()[1],(dataBase.getLastMouseClickPosition()[0]-dataBase.getLastMousePosition()[0]),(dataBase.getLastMousePosition()[1]-dataBase.getLastMouseClickPosition()[1]));
			}
		}
	}
	public void clearBox() {
		markingBox.setBounds(0,0,0,0);
	}
	public Rectangle getMarkingBox() {
		return markingBox;
	}

	/////////////////////////
	/////	
	/////	G E T T E R S
	/////
	/////////////////////////

	public Dimension getResolution() {
		return resolution;
	}

	public int getResolutionWidth(){
		return resolutionX;
	}
	public int getResolutionHeight(){
		return resolutionY;
	}

	public database.EngineDatabase getDataBase() {
		return dataBase;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public PopupMenu getPopup() {
		return popup;
	}

	/////////////////////////
	/////	
	/////	H E L P E R		M E T H O D S
	/////
	/////////////////////////
	

	public void drawGame() {
		mainPanel.repaint();
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public void setDatabase(database.EngineDatabase dataBase){
		this.dataBase=dataBase;
	}
	
	public void dispose(){
		mainFrame.dispose();
		mainFrame=null;
	}

	public void popUpMenu(MouseEvent e){
		popup.show(e.getComponent(), e.getX(), e.getY());
	}
	
}
