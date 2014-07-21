package collision;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Collider {
	
	String type;

	ArrayList<double[]> points=new ArrayList<double[]>(8);
	ArrayList<double[]> faces=new ArrayList<double[]>(8);
	ArrayList<double[]> normals=new ArrayList<double[]>(8);
	

	int nodeindex = 0;

	double posx;double posy;double posz;
	
	double direction = 0;

	double xOffset = 0;
	double yOffset = 0;

	double[] center = new double[2];
	
	boolean hasMoved = false;
	
	//com.infomatiq.jsi.Rectangle rTreeBounds;

	int gridID;
	
	ArrayList<Integer> nodeIndices = new ArrayList<Integer>(4);
	
	
	public Collider(){
	}


	//////////////////////////////
	//		G E O M E T R Y		//
	//////////////////////////////
	
	public Rectangle getBounds(){return null;}
	
	//public com.infomatiq.jsi.Rectangle getrTreeBounds() {return rTreeBounds;}

	public Shape getShape(){return null;}
	
	public Polygon getPolygon(){return null;}
	
	public Ellipse2D getCircle(){return null;}

	public void updateBounds(){}

	public double[] getCenter(){
		return center;
	}
	
	public List<double[]> getNormals(){
		return this.normals;
	}
	
	public void updateNormals() {
		for(int i=0;i<faces.size();i++){
			double length=Math.abs((Math.sqrt((faces.get(i)[0]*faces.get(i)[0])+(faces.get(i)[1]*faces.get(i)[1]))));
			normals.get(i)[0]=faces.get(i)[1]/length;normals.get(i)[1]=-faces.get(i)[0]/length;
		}
	}

	public List<double[]> getPoints(){
		return this.points;
	}
	public void updatePoints() {}

	public List<double[]> getFaces(){
		return this.faces;
	}
	public void updateFaces(){}

	public void initializeBounds(double posx, double posy, double posz) {
		updatePosition(posx,posy,posz);
	}
	
	//////////////////////////////////
	//		P O S I T I O N A L		//
	//////////////////////////////////

	public void setX(double nposx) {this.posx=nposx;}
	public double getX() {return this.posx;}
	public void setY(double nposy) {this.posy=nposy;}
	public double getY() {return this.posy;}
	public void setZ(double nposz) {this.posz=nposz;}
	public double getZ() {return this.posz;}

	public void updatePosition(double nposx,double nposy,double nposz){
		this.posx=nposx;this.posy=nposy;this.posz=nposz;
		updateBounds();updatePoints();updateFaces();
	}

	//////////////////////////////////////////////
	//			D I R E C T I O N A L			//
	//////////////////////////////////////////////

	public double getDirection() {return direction;}

	
	public void setDirection(double dir) {
		direction=dir;

		if(direction<0){direction=360+dir;}
		else if(direction>360){direction=(getDirection()+dir)-360;}

	}
	
	public void changeDirectionBy(double dir) {
		direction=direction+dir;

		if(direction<0){direction=360+dir;}
		else if(direction>360){direction=(getDirection()+dir)-360;}

	}
	
	public void rotate(){
		changeDirectionBy(0.2f);
	}
	public void rotaterandomly(){
		setDirection(randInt(0, 360));
	}
	//////HELPER
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void move(int[] current){
		setMoved(true);
		updatePosition(current[0],current[1],0);
		
	}
	
	public void moveBy(double a,double[] b){
		//Point2D.Double futurePoint=new Point2D.Double(getX()+(a*b[0]),getY()+(a*b[1]));
		//Point2D.Double futurePoint=new Point2D.Double(getX()+(a*b[0]),getY()+(a*b[1]));
		
		updatePosition((getX()+(a*b[0])),(getY()+(a*b[1])),0);
		
		/*if(this.getBounds().getMinX()<0||this.getBounds().getMaxX()>1540){
			
		}
		else if(this.getBounds().getMinY()<0||this.getBounds().getMaxY()>800){
			updatePosition((getX()+(a*b[0])),(getY()),0);
		}
		else{
			updatePosition((getX()+(a*b[0])),(getY()+(a*b[1])),0);
		}*/
		
		//System.out.println(Math.abs(Math.sqrt((b[0]*b[0])+(b[1]*b[1]))));
		//System.out.println("This thing moved by "+a+" on "+b[0]+" "+b[1]);
		//System.out.println(getX()+" "+getY());
		//System.out.println((getX()+(a*b[0]))+" "+(getY()+(a*b[1])));
		//System.out.println(b[0]+" "+b[1]);
	}
	
	/*private List<double[]> getUnitVectors(final List<double[]> vectors){
		final List<double[]> unitvectors = new ArrayList<double[]>();
		unitvectors.clear();
		for(int k=0;k<vectors.size();k++){
			final double length=Math.abs((Math.sqrt((vectors.get(k)[0]*vectors.get(k)[0])+(vectors.get(k)[1]*vectors.get(k)[1]))));
			final double[] unitvector=(new double[]{(vectors.get(k)[0]/length),(vectors.get(k)[1]/length)});
			unitvectors.add(unitvector);
		}
		return unitvectors;
	}*/
	
	/*private double getDotProduct(final double[] a,final double[] b){
		return ((a[0]*b[0])+(a[1]*b[1]));
	}*/

	public double[] getUnitVector(double[] vector){
		double length=Math.abs((Math.sqrt((vector[0]*vector[0])+(vector[1]*vector[1]))));
		return new double[]{(vector[0]/length),(vector[1]/length)};
	}

	public static int randInt(final int min, final int max) {
		final Random rand = new Random();
		final int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}


	public int getNodeindex() {
		return nodeindex;
	}


	public void setNodeindex(int nodeindex) {
		this.nodeindex = nodeindex;
	}

	public boolean hasMoved() {
		return hasMoved;
	}
	
	public void setMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	public int getGridID() {
		return gridID;
	}
	

	public void setGridID(int i) {
		gridID=i;
	}
	
}


