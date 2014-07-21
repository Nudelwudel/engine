package helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener{
	


	database.EngineDatabase dataBase;
	graphics.View view;

	UIController mUIController;

	public MainController(database.EngineDatabase engineDatabase) {
		this.dataBase=engineDatabase;
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		
	}
	
	public void pauseEngine(){
		
	}
	
	public UIController getUIController(){
		return this.mUIController;
	}
	public void createUIController(){
		mUIController=new UIController(dataBase,dataBase.getView());
	}
}
