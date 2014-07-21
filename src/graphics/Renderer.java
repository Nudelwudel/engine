package graphics;

import java.awt.Graphics2D;

import database.EngineDatabase;

public class Renderer {
	
	/*
	 * 
	 * 
	 * 
	 */
	
	Graphics2D g2;

	database.EngineDatabase dataBase;
	View view;


	public Renderer(EngineDatabase engineDatabase, View view) {
		this.dataBase=engineDatabase;
		this.view=view;
		
		
	}


	public void render() {
		
		g2=view.getMainPanel().getGraphics();

		g2.drawRect(100,100,50,50);
	
		
	}
	
	
	
	
	
}