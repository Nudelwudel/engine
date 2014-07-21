package helper;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class MainWindowListener implements WindowListener,WindowStateListener,WindowFocusListener{
	
	database.EngineDatabase dataBase;
	
	public MainWindowListener(database.EngineDatabase dataBase){
		this.dataBase=dataBase;
	}
	

	@Override
	public void windowActivated(WindowEvent arg0) {
		dataBase.getPlayerKeyListener().getKeysHeld().clear();
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
	}

	@Override
	public void windowStateChanged(WindowEvent arg0) {
	}

}
