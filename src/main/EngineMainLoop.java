package main;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import collision.Collider;
import collision.CollisionDetection;



public class EngineMainLoop {

	database.EngineDatabase dataBase;
	graphics.View view;

	CollisionDetection cd;

	boolean running = true;
	
	boolean paused = false;
	
	//fps
	int frameCount = 0;

	//List of all objects used for collision detection
	private final CopyOnWriteArrayList<Collider> allObjects = new CopyOnWriteArrayList<Collider>();
	

	public EngineMainLoop(final database.EngineDatabase dataBase){
		this.dataBase=dataBase;

		this.view=dataBase.getView();

		this.cd = new CollisionDetection(dataBase);

		dataBase.setCollisionDetection(cd);


		Thread loop = new Thread(){
			@Override
			public void run(){
				doMainLoop(dataBase);
			}
		};
		loop.start();

		//doMainLoop(dataBase);
	}

	//I pretty much stole the whole core main loop :(
	public void doMainLoop(database.EngineDatabase dataBase){

		//This value would probably be stored elsewhere.
		final double GAME_HERTZ = 60.0;
		//Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		//At the very most we will update the game this many times before a new render.
		//If you're worried about visual hitches more than perfect timing, set this to 1.
		final int MAX_UPDATES_BEFORE_RENDER = 1;
		//We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		//Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		//If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while(running){
			while (dataBase.getEngineStatus()==1){

				double now = System.nanoTime();
				int updateCount = 0;

				if (!paused)
				{
					//Do as many game updates as we need to, potentially playing catchup.
					while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
					{


						updateGame(lastUpdateTime,frameCount);

						lastUpdateTime += TIME_BETWEEN_UPDATES;

						updateCount++;

					}

					//If for some reason an update takes forever, we don't want to do an insane number of catchups.
					//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
					if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
					{
						lastUpdateTime = now - TIME_BETWEEN_UPDATES;
					}

					//Render. To do so, we need to calculate interpolation for a smooth render.
					//interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
					drawGame();
					frameCount++;

					lastRenderTime = now;

					//Update the frames we got.
					int thisSecond = (int) (lastUpdateTime / 1000000000);
					if (thisSecond > lastSecondTime)
					{
						//System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
						dataBase.setCurrentfps(frameCount);
						frameCount = 0;
						lastSecondTime = thisSecond;
					}

					//Yield until it has been at least the target time between renders. This saves the CPU from hogging.
					while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
					{
						Thread.yield();

						//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
						//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
						//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
						try {Thread.sleep(1);} catch(Exception e) {} 

						now = System.nanoTime();
					}
				}
			}
			while (dataBase.getEngineStatus() == 0) {

				try {Thread.sleep(1);} catch(Exception e) {} 
			}
			while (dataBase.getEngineStatus() == -1) {
				running=false;
				break;
			}


		}
		System.out.println("Shutting down...");
	}





	private void updateGame(double lastUpdateTime, int frameCount) {
		//prepare for collision detection
		allObjects.clear();

		//make controls work
		dataBase.getPlayerKeyListener().decideAction();



		//update all entities
		updateActors();
		updateVehicles();
		updateCollidersDEBUG();


		//solve collisions
		cd.checkAndSolveCollisions(allObjects);
		//System.out.println()
	}


	private void updateCollidersDEBUG() {
		//cd.getRTree().clearTree();

		for(Map.Entry<Integer, collision.Collider> collider  : dataBase.getAllCollidersMap().entrySet()){


			//cd.getRTree().addToRTree(collider.getValue());
			/*if(Math.random()>0.95){
					if(Math.random()>0.5){
						collider.getValue().moveBy(1, new double[]{1,0});
					}
					else{
						collider.getValue().moveBy(1, new double[]{-1,0});
					}
				}
				if(Math.random()>0.95){
					if(Math.random()>0.5){
						collider.getValue().moveBy(1, new double[]{0,1});
					}
					else{
						collider.getValue().moveBy(1, new double[]{0,-1});
					}
				}*/

			//OUT OF BOUNDS CHECK
			//if(collider.getValue().hasMoved()){
			if(collider.getValue().getBounds().getMinX()<0 || collider.getValue().getBounds().getMinY()<0){
				if(collider.getValue().getBounds().getMinX()<0){
					collider.getValue().updatePosition((collider.getValue().getX()+2),(collider.getValue().getY()),0);
				}
				else if(collider.getValue().getBounds().getMinY()<0){
					collider.getValue().updatePosition((collider.getValue().getX()),(collider.getValue().getY()+2),0);
				}
			}
			else if(collider.getValue().getBounds().getMaxX()>1540 || collider.getValue().getBounds().getMaxY()>800) {
				if(collider.getValue().getBounds().getMaxX()>1540){
					collider.getValue().updatePosition((collider.getValue().getX()-2),(collider.getValue().getY()),0);
				}
				else if(collider.getValue().getBounds().getMaxY()>800){
					collider.getValue().updatePosition((collider.getValue().getX()),(collider.getValue().getY()-2),0);
				}
			}
			//}

			allObjects.add(collider.getValue());
			//allCollidersMap.put(collider.getValue().getGridID(), collider.getValue());
			//System.out.println(collider.getValue().getX()+" "+collider.getValue().getY());
		}
	
		//System.out.println(move);
		//System.out.println("all colliders: "+allCollidersMap.size());
	}

	private void updateActors() {
		for(Map.Entry<Integer, entities.Actor> a  : dataBase.getActorList().entrySet()){
			entities.Actor actor = a.getValue();
			if(actor.isAlive()){
				if(Math.random()>0.95){
					if(Math.random()>0.5){
						actor.moveBy(1, new double[]{1,0});
					}
					else{
						actor.moveBy(1, new double[]{-1,0});
					}
				}
				if(Math.random()>0.95){
					if(Math.random()>0.5){
						actor.moveBy(1, new double[]{0,1});
					}
					else{
						actor.moveBy(1, new double[]{0,-1});
					}
				}
				//is not in the marking box -> unmark
				if(dataBase.isMousePressedDown()&&!view.getMarkingBox().intersects(actor.getBounds())){
					actor.setMarked(false);
					dataBase.getMarkedActors().remove(a);
				}
				//is not marked but in marking box -> add to marked
				if(!actor.getMarked()&&view.getMarkingBox().intersects(actor.getBounds())){
					actor.setMarked(true);
					dataBase.getMarkedActors().add(actor);
				}

				//check if out of bounds
				if(actor.getBounds().getMinX()<0 || actor.getBounds().getMinY()<0){
					if(actor.getBounds().getMinX()<0){
						actor.updatePosition((actor.getX()+2),(actor.getY()),0);
					}
					else if(actor.getBounds().getMinY()<0){
						actor.updatePosition((actor.getX()),(actor.getY()+2),0);
					}
				}
				else if(actor.getBounds().getMaxX()>1540 || actor.getBounds().getMaxY()>800) {
					if(actor.getBounds().getMaxX()>1540){
						actor.updatePosition((actor.getX()-2),(actor.getY()),0);
					}
					else if(actor.getBounds().getMaxY()>800){
						actor.updatePosition((actor.getX()),(actor.getY()-2),0);
					}
				}

				//add to all objects for collision detection
				allObjects.add(actor);
				//allCollidersMap.put(actor.getGridID(), actor);

			}
			//if dead -> delete actor out of every relevant list
			else{dataBase.getActorList().remove(a);dataBase.getMarkedActors().remove(a);}
		}
	}

	private void updateVehicles() {
		for(entities.Vehicle v : dataBase.getVehicleList()){
			//allObjects.add(v);
		}
	}

	private void drawGame() {
		dataBase.getView().drawGame();
	}







	public CollisionDetection getCd() {
		return cd;
	}



}
