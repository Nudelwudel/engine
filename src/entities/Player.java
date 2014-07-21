package entities;

import database.EngineDatabase;

public class Player extends Actor{

	public Player(int posx, int posy, double width, String side,EngineDatabase dataBase) {
		super(posx, posy, width, side, dataBase);
	}

}
