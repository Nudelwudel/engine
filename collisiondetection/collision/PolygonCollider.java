package collision;

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;


public class PolygonCollider extends Collider{

	collision.Polygon2D poly = new collision.Polygon2D.Double();
	int pointCount=0;

	List<double[]> originalPoints = new ArrayList<double[]>(8);

	public PolygonCollider(int[] xpoints, int[] ypoints){

		//points
		pointCount=xpoints.length;
		for(int i=0;i<pointCount;i++){
			if(i==0){
				originalPoints.add(new double[]{xpoints[i],ypoints[i]});
				points.add(new double[]{xpoints[i],ypoints[i]});
				poly.moveTo(points.get(i)[0],points.get(i)[1]);
			}
			else{
				originalPoints.add(new double[]{xpoints[i],ypoints[i]});
				points.add(new double[]{xpoints[i],ypoints[i]});
				poly.lineTo(points.get(i)[0],points.get(i)[1]);
			}
		}

		//faces
		for(int i=0;i<pointCount;i++){
			if(i==pointCount-1){
				faces.add(new double[]{points.get(0)[0]-points.get(i)[0],points.get(0)[1]-points.get(i)[1]});
				normals.add(new double[]{points.get(0)[1]-points.get(i)[1],-(points.get(0)[0]-points.get(i)[0])});
			}else{
				faces.add(new double[]{points.get(i+1)[0]-points.get(i)[0],points.get(i+1)[1]-points.get(i)[1]});
				normals.add(new double[]{points.get(i+1)[1]-points.get(i)[1],-(points.get(i+1)[0]-points.get(i)[0])});
			}
		}
		setX(xpoints[0]);setY(ypoints[0]);

		setType("Polygon");
		updatePosition(xpoints[0],ypoints[0],0);
	}

	//////////////////////////////
	//		G E O M E T R Y		//
	//////////////////////////////
	@Override
	public Rectangle getBounds(){return poly.getBounds();}
	//@Override
	//public Polygon getPolygon(){return poly.;}
	@Override
	public Shape getShape(){return poly;}


	@Override
	public void updateBounds(){
		
		//poly.getBounds().setLocation((int)Math.round(posx),(int)Math.round(posy));
		//rTreeBounds.set((float)posx,(float)posy,(float)(posx+poly.getBounds().getWidth()),(float)(posy+poly.getBounds().getHeight()));
		//poly.getBounds().setLocation((int)posx,(int)posy);
	}
	@Override
	public double[] getCenter(){
		return center;
	}



	@Override
	public List<double[]> getPoints(){
		return this.points;
	}
	@Override
	public void updatePoints() {
		//future points for center of polygon
		double allX=0;double allY=0;
		//move polygon by Offsets;
		
		poly.translate(xOffset,yOffset);

		//update every virtual point
		for(int i=0;i < pointCount;i++){
			getPoints().get(i)[0]+=xOffset;getPoints().get(i)[1]+=yOffset;
			allX+=getPoints().get(i)[0];allY+=getPoints().get(i)[1];
		}


		center[0]=allX/pointCount;
		center[1]=allY/pointCount;

	}

	@Override
	public List<double[]> getFaces(){
		return this.faces;
	}
	@Override
	public void updateFaces(){
		for(int i=0;i < pointCount-1; i++){
			if(i==pointCount-1){
				faces.get(i)[0]=points.get(0)[0]-points.get(i)[0];
				faces.get(i)[1]=points.get(0)[1]-points.get(i)[1];
			}else{
				faces.get(i)[0]=points.get(i+1)[0]-points.get(i)[0];
				faces.get(i)[1]=points.get(i+1)[1]-points.get(i)[1];
			}
		}
	}

	//////////////////////////////////
	//		P O S I T I O N A L		//
	//////////////////////////////////S

	@Override
	public void updatePosition(double nposx,double nposy,double nposz){
		xOffset=nposx-posx;yOffset=nposy-posy;
		//System.out.println("nposx "+nposx+" nposy "+nposy);
		setX(nposx);setY(nposy);this.posz=nposz;

		updateBounds();updatePoints();updateFaces();
		setMoved(true);
	}

	//ROTATION
	/*
	 * Let's keep this for later until the important stuff is implemented
	 
	 
	public void rotate(List<double[]> originalPoints,double angle){

		/* We ge the original points of the polygon we wish to rotate
		 *  and rotate them with affine transform to the given angle. 
		 *  After the opeariont is complete the points are stored to the 
		 *  array given to the method.
		 
		Point[] storeTo = new Point[pointCount];
		Point[] origPoints = new Point[pointCount];

		for(int i=0;i<originalPoints.size();i++){
			origPoints[i]=new Point((int)originalPoints.get(i)[0],(int) originalPoints.get(i)[1]);
			storeTo[i]=new Point();
		}

		AffineTransform.getRotateInstance
		(Math.toRadians(angle), getCenter()[0],getCenter()[1])
		.transform(origPoints,0,storeTo,0,pointCount);


		//polygonize(storeTo);
	}*/

	/*public void polygonize(Point[] polyPoints){

		//a simple method that makes a new polygon out of the rotated points
		Polygon rotatedtemp = new Polygon();
		for(int  i=0; i < polyPoints.length; i++){
			rotatedtemp.addPoint(polyPoints[i].x, polyPoints[i].y);
		}
		this.poly=rotatedtemp;
	}*/

	public List<double[]> getOriginalPoints() {
		return originalPoints;
	}
}
