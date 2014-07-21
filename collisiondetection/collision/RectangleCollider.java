package collision;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;

public class RectangleCollider extends Collider{

	Rectangle rect;
	//com.infomatiq.jsi.Rectangle rTreeBounds;
	double[] p1=new double[2];double[] p2=new double[2];double[] p3=new double[2];double[] p4=new double[2];
	double[] f1=new double[2];double[] f2=new double[2];double[] f3=new double[2];double[] f4=new double[2];
	
	
	double angle=0;

	public RectangleCollider(int posx,int posy,int posz,double width,double height){
		//actually initialize the rectangle
		rect=new Rectangle(posx,posy,(int)width,(int)height);
		//
		points.add(p1);points.add(p2);points.add(p3);points.add(p4);
		p1[0]=posx;p1[1]=posy;p2[0]=posx+width;p2[1]=posy+width;p3[0]=posx+width;p3[1]=posy+height;p4[0]=posx;p4[1]=posy+height;
	
		faces.add(f1);faces.add(f2);faces.add(f3);faces.add(f4);
		this.f1[0]=this.p2[0]-this.p1[0];	this.f1[1]=this.p2[1]-this.p1[1];
		this.f2[0]=this.p3[0]-this.p2[0];	this.f2[1]=this.p3[1]-this.p2[1];
		this.f3[0]=this.p4[0]-this.p3[0];	this.f3[1]=this.p4[1]-this.p3[1];
		this.f4[0]=this.p1[0]-this.p4[0];	this.f4[1]=this.p1[1]-this.p4[1];

		setType("Rectangle");
		initializeBounds(posx,posy,posz);
	}

	//////////////////////////////
	//		G E O M E T R Y		//
	//////////////////////////////
	@Override
	public Rectangle getBounds(){return rect.getBounds();}
	
	@Override
	public Shape getShape(){return rect;}

	@Override
	public void updateBounds(){
		rect.setLocation((int)this.posx,(int)this.posy);
		//rTreeBounds.set((float)posx,(float)posy,(float)(posx+rect.getWidth()),(float)(posy+rect.getHeight()));
	}
	@Override
	public double[] getCenter(){
		return new double[]{rect.getCenterX(),rect.getCenterY()};
	}



	@Override
	public List<double[]> getPoints(){
		return this.points;
	}
	@Override
	public void updatePoints() {
		p1[0]=posx;p1[1]=posy;p2[0]=posx+rect.getWidth();p2[1]=posy+rect.getWidth();
		p3[0]=posx+rect.getWidth();p3[1]=posy+rect.getHeight();p4[0]=posx;p4[1]=posy+rect.getHeight();

	}

	@Override
	public List<double[]> getFaces(){
		return this.faces;
	}
	@Override
	public void updateFaces(){
		this.f1[0]=this.p2[0]-this.p1[0];	this.f1[1]=this.p2[1]-this.p1[1];
		this.f2[0]=this.p3[0]-this.p2[0];	this.f2[1]=this.p3[1]-this.p2[1];
		this.f3[0]=this.p4[0]-this.p3[0];	this.f3[1]=this.p4[1]-this.p3[1];
		this.f4[0]=this.p1[0]-this.p4[0];	this.f4[1]=this.p1[1]-this.p4[1];
	}




}
