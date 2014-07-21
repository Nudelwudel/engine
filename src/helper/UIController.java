package helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIController implements ActionListener {

	database.EngineDatabase dataBase;
	graphics.View view;

	public UIController(database.EngineDatabase dataBase, graphics.View view){
		this.dataBase=dataBase;
		this.view=view;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String actioncommand=ae.getActionCommand();
		System.out.println(actioncommand);
		if(actioncommand.equals("Spawn Actor")){
			dataBase.getActorFactory().createActors(1,dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Side1)")){
			dataBase.getActorFactory().createActors(1,dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Side2)")){
			dataBase.getActorFactory().createActors(1,dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Group)")){
			dataBase.getActorFactory().createActors(10,dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Group, Side1)")){
			dataBase.getActorFactory().createActors(10,"side1",dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Group, Side2)")){
			dataBase.getActorFactory().createActors(10,"side2",dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Mob)")){
			dataBase.getActorFactory().createActors(100,dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Mob, Side1)")){
			dataBase.getActorFactory().createActors(100,"side1",dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("Spawn Actor (Mob, Side2)")){
			dataBase.getActorFactory().createActors(100,"side2",dataBase.getLastMouseClickPosition()[0],dataBase.getLastMouseClickPosition()[1]);
		}
		 if(actioncommand.equals("testMethod")){
			dataBase.getCollisionDetection().testMethod();
		}
		/*if(actioncommand.equals("Spawn Vehicle")){
			
			entities.Vehicle v = new Vehicle();
		}*/

		if(actioncommand.equals("Start")){
			dataBase.setEngineStatus(1);System.out.println(actioncommand);
			dataBase.start();
		}
		if(actioncommand.equals("Quit")){
			dataBase.setEngineStatus(-1);System.out.println(actioncommand);
			
			try{
				dataBase.exit();
				dataBase.getView().dispose();
			}catch(NullPointerException e){
				System.out.println(view);
				e.printStackTrace();
			}
			
		}
		if(actioncommand.equals("Pause")){
			if(dataBase.getEngineStatus()!=0){
				dataBase.setEngineStatus(0);
			}
			{
				dataBase.setEngineStatus(1);
			}

		}

	}

}
