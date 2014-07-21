package factories;

import java.util.Random;

import entities.Actor;

public class ActorFactory {


	database.EngineDatabase dataBase;

	public ActorFactory(database.EngineDatabase dataBase){
		this.dataBase=dataBase;

	}

	///////////////
	//	random actors
	///////////////
	public void createActors(int count){
		for(int i=0;i<count;i++){
			final Actor a = new Actor(randInt(0,1000), randInt(0,800),15,"none",dataBase);
			dataBase.addActor(a);
		}
	}

	///////////////
	//	random actors at x/y
	///////////////
	public void createActors(int count,int posx,int posy){

		for(int i=0;i<count;i++){
			final Actor a = new Actor(posx+randInt(0,250),posy+randInt(0,250),15,"none",dataBase);
			dataBase.addActor(a);
		}
	}

	///////////////
	//	actors with side and position
	///////////////
	public void createActors(int count,String side,int posx,int posy){

		for(int i=0;i<count;i++){
			final Actor a = new Actor(posx+randInt(0,250),posy+randInt(0,250),15,side,dataBase);
			dataBase.addActor(a);
		}
	}


	public static int randInt(final int min, final int max) {
		final Random rand = new Random();
		final int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
