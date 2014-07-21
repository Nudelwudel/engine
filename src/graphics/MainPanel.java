package graphics;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;

import entities.Actor;

public class MainPanel extends JComponent{

	/*
	 * 
	 * Main game panel where the action will get drawn
	 * 
	 * 
	 */

	View view;

	database.EngineDatabase dataBase;

	Renderer renderer;

	Graphics2D g2;


	public MainPanel(View view){
		setLayout(null);
		this.view=view;
		this.dataBase=view.getDataBase();

		renderer = view.getDataBase().getRenderer();
		setFocusable(true);

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		//essential stuff
		super.paintComponent(g);
		this.g2=(Graphics2D) g;

		//Draw marking box (if mouse is pressed)
		g2.setColor(Color.black);
		if(dataBase.isMousePressedDown()){
			g2.draw(view.getMarkingBox());
		}
		//AA
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		//draw all actors
		for(Map.Entry<Integer, entities.Actor> a  : dataBase.getActorList().entrySet()){
			drawActor(a.getValue());
		}

		//some stuff for testing
		debugDrawing();



		//draw FPS
		g2.drawString(String.valueOf(dataBase.getCurrentfps()), 10, 10);
		//draw center
		g2.drawString(dataBase.getLastMousePosition()[0]+"|"+dataBase.getLastMousePosition()[1], dataBase.getLastMousePosition()[0], dataBase.getLastMousePosition()[1]);
	}


	//////////////////////////////////////////////////////////////////
	//	S P E C I F I C		D R A W I N G 	M E T H O D S
	//////////////////////////////////////////////////////////////////
	
	private void drawActor(entities.Actor a) {
		g2.setColor(Color.black);
		//get the right image
		g2.drawImage(a.getImage(),null,(int)a.getX(),(int)a.getY());
		//draw their bounds for now
		g2.draw(a.getShape());
		//healthbar and marking
		drawHealthbarAndMarking(a);
	}

	private void drawHealthbarAndMarking(entities.Actor a) {
		//TO DO: find offsets & top or bottom?
		int posx = (int)a.getX();
		int posy = (int)a.getY();
		/*g2.setColor(Color.black);
		g2.drawRect(posx+1, posy+20, 11, 4);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(posx+2, posy+21, 10, 3);
		if(a.getHealth()>75){g2.setColor(Color.green);g2.fillRect(posx+2, posy+21, (int)Math.ceil(a.getHealth()/10), 3);}
		else if(a.getHealth()>25&&a.getHealth()<=75){g2.setColor(Color.yellow);g2.fillRect(posx+2, posy+21, (int)Math.ceil(a.getHealth()/10), 3);}
		else{g2.setColor(Color.red);g2.fillRect(posx+2, posy+21, (int)Math.ceil(a.getHealth()/10), 3);}*/

		g2.setColor(Color.orange);
		if(a.getMarked()){
			g2.drawRect(posx-2, posy-2, (int)a.getBounds().getWidth()+4,(int)a.getBounds().getHeight()+4);
		}
	}

	

	
	
	// weird shit below
	
	//shouldnt use this
	@Override
	public Graphics2D getGraphics(){
		return g2;
	}
	
	
	//keep this around
	private void debugDrawing() {
		//DEBUG draw all colliders
		for(Map.Entry<Integer, collision.Collider> collider  : dataBase.getAllCollidersMap().entrySet()){
			//int x=0;
			//g2.drawString("Rectangle :"+collider.getValue().getRTreeID(), collider.getValue().getBounds().x-5, collider.getValue().getBounds().y-5);
			//g2.drawString(String.valueOf(c.getNodeindex()), (int)c.getCenter()[0]-5, (int)c.getCenter()[1]+5);
			//g2.drawRect((int)collider.getValue().getCenter()[0], (int)collider.getValue().getCenter()[1], 2, 2);
			//g2.drawString((int)collider.getValue().getCenter()[0]+"/"+(int)collider.getValue().getCenter()[1],(int)collider.getValue().getCenter()[0],(int)collider.getValue().getCenter()[1]);
			/*if(c.getType().equals("Circle")){
						collision.CircleCollider circle = (collision.CircleCollider) c;
						g2.drawLine((int)c.getCenter()[0],(int)c.getCenter()[1], (int)(c.getCenter()[0]+circle.getRadius()), (int)c.getCenter()[1]);
						g2.drawLine((int)c.getCenter()[0],(int)c.getCenter()[1],(int)c.getCenter()[0], (int)(c.getCenter()[1]+circle.getRadius()));
					}*/
			/*for(double[] p : collider.getValue().getPoints()){
						g2.drawString(p[0]+"/"+p[1], (int)p[0]-10, (int)p[1]-10);
						//g2.drawLine((int)p[0], (int)p[1], (int)p[0]+(int)c.getFaces().get(x)[0],(int)p[1]+(int)c.getFaces().get(x)[1]);

						x++;
					}*/
			g2.draw(collider.getValue().getShape());
			//g2.setColor(Color.green);
			//g2.draw(collider.getValue().getBounds());
			g2.setColor(Color.black);
		}
		//Debug visualisation for grid
		/*if(dataBase.getCollisionDetection()!=null){
					g2.setColor(Color.black);
					for(Rectangle r : dataBase.getCollisionDetection().getGrid().getRectangles()){
						g2.draw(r);
					}

					for(collision.Node n : dataBase.getCollisionDetection().getGrid().getNodes()){
						//g2.drawString(String.valueOf(n.getCellId()),n.getRect().x+5,n.getRect().y+15);
						//g2.drawString(String.valueOf(n.getObjects().size()),n.getRect().x+5,n.getRect().y+30);
					}
				}*/

	}


}
