package input;



import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

import javax.swing.SwingUtilities;

public class MainMouseListener implements MouseListener,MouseMotionListener{
	
	database.EngineDatabase dataBase;
	graphics.View view;

	double angle=45;

	public MainMouseListener(database.EngineDatabase dataBase) {
		this.dataBase = dataBase;
		view=dataBase.getView();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dataBase.setLastMouseClickPosition(e);
		//on any click in mainpanel -> get focus
		view.getMainPanel().requestFocusInWindow();

		if(SwingUtilities.isRightMouseButton(e)){
			//on rightclick -> popupmenu
			view.popUpMenu(e);
		}
		else if(SwingUtilities.isLeftMouseButton(e)){
			//if leftclick -> cleark marking box, clear marked actors, 
			view.clearBox();
			dataBase.getMarkedActors().clear();
			
			//add to marked for single actor
			for(Map.Entry<Integer, entities.Actor> a  : dataBase.getActorList().entrySet()){
				entities.Actor actor = a.getValue();
				if(actor.getBounds().contains(new Point(e.getX(),e.getY()))){
					actor.setMarked(true);
					dataBase.getMarkedActors().add(actor);
				}
				else{
					actor.setMarked(false);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dataBase.setLastMouseClickPosition(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dataBase.setMousePressedDown(false);
		view.clearBox();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//only register mouse being pressed down here so the marked actors stay the same
		dataBase.setLastMousePosition(e);
		dataBase.setMousePressedDown(true);
	
		view.createBox();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dataBase.setLastMousePosition(e);
	}

}
