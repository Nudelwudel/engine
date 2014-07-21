package collision;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;



public class CircleCollider extends Collider{

	Ellipse2D.Double circle;
	double radius;
	double diameter;
	boolean oncreation=true;



	public CircleCollider(int posx,int posy,double diameter){
		circle = new Ellipse2D.Double(posx,posy,diameter,diameter);
		
		this.diameter=diameter;
		radius=diameter/2;
		
		//rTreeBounds=new com.infomatiq.jsi.Rectangle(posx,posy,(int)(posx+diameter),(int)(posy+diameter));

		setType("Circle");
		updatePosition(posx,posy,0);
	}


	//////////////////////////////
	//		G E O M E T R Y		//
	//////////////////////////////
	@Override
	public Rectangle getBounds(){return circle.getBounds();}
	@Override
	public Ellipse2D getCircle(){return circle;}
	@Override
	public Shape getShape(){return circle;}

	@Override
	public void updateBounds(){
		circle.x=posx;
		circle.y=posy;
		//rTreeBounds=new com.infomatiq.jsi.Rectangle((float)posx,(float)posy,(int)(posx+diameter),(int)(posy+diameter));
		//rTreeBounds.set((float)circle.x, (float)circle.y, (float)(circle.x+diameter), (float)(circle.y+diameter));
	}
	@Override
	public double[] getCenter(){
		return center;
	}

	@Override
	public void updatePoints() {
		center[0]=circle.getCenterX();
		center[1]=circle.getCenterY();
	}

	//////////////////////////////////
	//		P O S I T I O N A L		//
	//////////////////////////////////
	
	public double getRadius() {
		return radius;
	}
	@Override
	public void updatePosition(double nposx,double nposy,double nposz){
		xOffset=nposx-posx;yOffset=nposy-posy;

		//System.out.println("old: "+posx+" "+posy+" new: "+nposx+" "+nposy);
		setX(nposx);setY(nposy);this.posz=nposz;

		updateBounds();updatePoints();updateFaces();
		setMoved(true);
		//System.out.println(xOffset+" "+yOffset);
	}

	@Override
	public List<double[]> getFaces(){
		return this.faces;
	}
	@Override
	public void updateFaces(){
	}

}
