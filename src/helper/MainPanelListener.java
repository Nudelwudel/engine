package helper;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class MainPanelListener implements ComponentListener {
	
	database.EngineDatabase dataBase;

	public MainPanelListener(database.EngineDatabase dataBase){
		this.dataBase=dataBase;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {
		//dataBase.getView().getMainFrame().requestFocusInWindow();
	}

}
