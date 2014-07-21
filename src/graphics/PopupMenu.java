package graphics;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class PopupMenu extends JPopupMenu {
	helper.UIController uiController;
	
	
	private final JMenuItem move=new JMenuItem("Attack Move here!");
	
	private final JMenu actors = new JMenu("Spawn Actors");
	private final JMenu actorsingle = new JMenu("Spawn Actor");
	private final JMenu actorgroup = new JMenu("Spawn Actors (Group)");
	private final JMenu actormob = new JMenu("Spawn Actors (Mob)");

	private final JMenuItem singleactor = new JMenuItem("Spawn Actor");
	private final JMenuItem singleactors1 = new JMenuItem("Spawn Actor (Side1)");
	private final JMenuItem singleactors2 = new JMenuItem("Spawn Actor (Side2)");
	
	private final JMenuItem actorg = new JMenuItem("Spawn Actor (Group)");
	private final JMenuItem actorgs1 = new JMenuItem("Spawn Actor (Group, Side1)");
	private final JMenuItem actorgs2 = new JMenuItem("Spawn Actor (Group, Side2)");
	
	private final JMenuItem actorm = new JMenuItem("Spawn Actor (Mob)");
	private final JMenuItem actorms1 = new JMenuItem("Spawn Actor (Mob, Side1)");
	private final JMenuItem actorms2 = new JMenuItem("Spawn Actor (Mob, Side2)");
	
	private final JMenu vehicles = new JMenu("Spawn Vehicles");
	
	private final JMenuItem singlevehicle = new JMenu("Spawn Vehicle");

	private final JMenu dbg = new JMenu("Debug Stuff");
	private final JMenuItem drawdbg = new JMenuItem("drawDebugInfo");
	private final JMenuItem testmthd = new JMenuItem("testMethod");


	public PopupMenu(helper.MainController newcontroller){

		add(move);
		move.addActionListener(uiController);
		
		add(actors);
		actors.add(actorsingle);actors.add(actorgroup);actors.add(actormob);
		
		actorsingle.add(singleactor);
		actorsingle.add(singleactors1);
		actorsingle.add(singleactors2);
		
		actorgroup.add(actorg);
		actorgroup.add(actorgs1);
		actorgroup.add(actorgs2);
		
		actormob.add(actorm);
		actormob.add(actorms1);
		actormob.add(actorms2);
		
		add(vehicles);
		vehicles.add(singlevehicle);

		add(dbg);
		
		dbg.add(drawdbg);
		dbg.add(testmthd);
		
	}
	
	public void buildControls(){
		singleactor.addActionListener(uiController);
		singleactors1.addActionListener(uiController);
		singleactors2.addActionListener(uiController);
		
		actorg.addActionListener(uiController);
		actorgs1.addActionListener(uiController);
		actorgs2.addActionListener(uiController);
		
		actorm.addActionListener(uiController);
		actorms1.addActionListener(uiController);
		actorms2.addActionListener(uiController);
		
		
		singlevehicle.addActionListener(uiController);
		
		testmthd.addActionListener(uiController);
		drawdbg.addActionListener(uiController);
	}
	
	public void addUIController(helper.UIController uiController){
		this.uiController=uiController;
		
		buildControls();
	}

}
