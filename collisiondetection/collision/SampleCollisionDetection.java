package collision;


public class SampleCollisionDetection {

	/*private  ExecutorService hdpool = Executors.newFixedThreadPool(2);


	private CopyOnWriteArrayList<CopyOnWriteArrayList<Collider>> returnObjects = new CopyOnWriteArrayList<CopyOnWriteArrayList<Collider>>();


	database.EngineDatabase dataBase;
	CollisionGrid grid;

	int counter=0;

	public CollisionDetection(database.EngineDatabase dataBase) {

		this.dataBase=dataBase;
		grid=new CollisionGrid(0,0,1600,800);
		//rTree = new RTreeClass(dataBase);
	}


	public CollisionGrid getGrid() {
		return grid;
	}


	@SuppressWarnings("unchecked")
	public void checkAndSolveCollisions(final ConcurrentHashMap<Integer, Collider> allColliders) {
		long check1=System.currentTimeMillis();
		//grid.rebuildEntries();
		for(final Map.Entry<Integer, collision.Collider> collider  : allColliders.entrySet()){
			//System.out.println(grid.findObject(collider.getValue()).size());
			if(collider.getValue().hasMoved){
				grid.updateEntry(collider.getValue());
				//grid.findObject(collider.getValue());
				returnObjects.add(grid.getNearestObjects(collider.getValue()));
				//System.out.println("fff: "+collider.getValue().getNodeIndices().size()+" ");
			}

			collider.getValue().setMoved(false);

		}


		if(!returnObjects.isEmpty()){

			if(returnObjects.size()>50){
				int length = returnObjects.size();

				for(int i=0;i<returnObjects.size();i++){
					hdpool.execute(new solveCollision(returnObjects.get(i)));
				}
				returnObjects.clear();
			}
			else{
				for(int i=0;i<returnObjects.size();i++){

					//System.out.println("No. of collisions: "+returnObjects.size());
					//
					//System.out.println("Colliding objects: "+returnObjects.get(i).size());
					new solveCollision(returnObjects.get(i));
				}
				returnObjects.clear();
			}
			
			
		}

		long check2=System.currentTimeMillis();
		if(counter%100==0){
			System.out.println("Collision took: "+(check2-check1)+"ms");
		}


		counter++;



		
	}

	class solveCollision implements Runnable{

		CopyOnWriteArrayList<Collider> returnObjectsForThread = new CopyOnWriteArrayList<Collider>();

		public solveCollision(CopyOnWriteArrayList<Collider> returnObj){
			returnObjectsForThread=returnObj;
			//System.out.println("returnObj in Collision: "+returnObj.size());
			run();
		}

		@Override
		public void run(){
			for(int x=0;x<returnObjectsForThread.size()-1;x++) {

				boolean rectangleAtX=false;
				boolean circleAtX=false;
				boolean polygonAtX=false;

				switch(returnObjectsForThread.get(x).getType()){
				case("Circle"):circleAtX=true;
				case("Polygon"):polygonAtX=true;
				case("Rectangle"):rectangleAtX=true;
				}

				for(int y=x+1;y<returnObjectsForThread.size();y++) {
					if(returnObjectsForThread.get(x).equals(returnObjectsForThread.get(y))){
						break;
					}
					else{
						boolean rectangleAtY=false;
						boolean circleAtY=false;
						boolean polygonAtY=false;

						switch(returnObjectsForThread.get(y).getType()){
						case("Circle"):circleAtY=true;
						case("Polygon"):polygonAtY=true;
						case("Rectangle"):rectangleAtY=true;
						}
						//System.out.println(returnObjectsForThread.get(x).getType()+" "+returnObjectsForThread.get(y).getType());

						if((circleAtX&&circleAtY)) {
							//System.out.println("returnObjectsForThread.get(x) "+returnObjectsForThread.get(x).getX()+" returnObjectsForThread.get(y): "+returnObjectsForThread.get(y).getX());
							solveCircleCircle(returnObjectsForThread.get(x),returnObjectsForThread.get(y));
						}
						else if((circleAtX&&rectangleAtY)||(circleAtX&&polygonAtY)){
							solveCirclePolygon(returnObjectsForThread.get(x),returnObjectsForThread.get(y));
						}
						else if((polygonAtX&&circleAtY)||(rectangleAtX&&circleAtY)){
							solveCirclePolygon(returnObjectsForThread.get(y),returnObjectsForThread.get(x));
						}
						else if((circleAtX&&polygonAtY)){
							solveCirclePolygon(returnObjectsForThread.get(x),returnObjectsForThread.get(y));
						}
						else if(polygonAtX&&polygonAtY){
							solvePolygonPolygon(returnObjectsForThread.get(x),returnObjectsForThread.get(y));
						}
						else if(polygonAtX&&rectangleAtY){
							solvePolygonRectangle(returnObjectsForThread.get(x),returnObjectsForThread.get(y));
						}
					}
				}
			}
		}


		private void solvePolygonRectangle(Collider c1, Collider c2) {

		}


		private void solvePolygonPolygon(Collider c1, Collider c2) {
			PolygonCollider poly1 = (PolygonCollider) c1;
			PolygonCollider poly2 = (PolygonCollider) c2;
			List<double[]> allNormals=new ArrayList<double[]>();

			double overlap = Double.MAX_VALUE;
			double[] smallest=null;

			List<double[]> faces=poly1.getFaces(),faces2=poly2.getFaces();



			faces=c1.getFaces();
			allNormals.addAll(getUnitVectors(getNormals(faces)));
			allNormals.addAll(getUnitVectors(getNormals(faces2)));


			int hit=0;

			for(int u=0;u<allNormals.size();u++){

				double[] p1=getPolyProjection(c1,faces,allNormals.get(u));
				double[] p2=getPolyProjection(c2,faces2,allNormals.get(u));

				// check for containment
				if (poly1.getBounds().contains(poly2.getBounds()) || poly2.getBounds().contains(poly1.getBounds())) {
					// get the overlap plus the distance from the minimum end points
					double mins = Math.abs(p1[0] - p2[0]);
					double maxs = Math.abs(p1[1] - p2[1]);
					// NOTE: depending on which is smaller you may need to
					// negate the separating axis!!
					if (mins < maxs) {
						overlap += mins;
					} else {
						overlap += maxs;
					}
				}

				double[] cacb = new double[]{(c2.getCenter()[0]-c1.getCenter()[0]),(c2.getCenter()[1]-c1.getCenter()[1])}; // the vector from ca to cb

				if(!(overlaps(p1,p2)<0)){
					break; 
				}else{
					hit++;

					double o=(overlaps(p1,p2));

					if (Math.abs(o) < overlap) {
						overlap = Math.abs(o);

						smallest=allNormals.get(u);
						if (getDotProduct(cacb,smallest) < 0) {
							smallest=new double[]{-smallest[0],-smallest[1]};
						}
					}
				}

				//for a polygon every axis has to not have any overlap
				//for a rectangle only two axis
				if(hit==allNormals.size()){
					c1.moveBy(-overlap, smallest);
					//c2.moveBy(overlap, smallest);
					//System.out.println("c1 moved by: -"+overlap+" on "+smallest[0]+" "+smallest[1]);
					//System.out.println("c2 moved by: "+overlap+" on "+smallest[0]+" "+smallest[1]);
				}
			}
		}

		private void solveCirclePolygon(Collider c1, Collider c2) {
			CircleCollider circle = (CircleCollider) c1;
			PolygonCollider poly = (PolygonCollider) c2;

			List<double[]> allNormals=new ArrayList<double[]>();

			double overlap = Double.MAX_VALUE;
			double[] smallest=null;

			allNormals.addAll(getUnitVectors(getNormals(poly.getFaces())));

			//Circle Center to Edge Points
			ArrayList<double[]> otheraxes=new ArrayList<double[]>();

			double tempdistance=0;
			double distance=Double.MAX_VALUE;

			for(double[] point : poly.getPoints()){

				tempdistance=Math.sqrt(Math.pow(point[0]-circle.getCenter()[0],2)+Math.pow(point[1]-circle.getCenter()[1], 2));
				if(tempdistance<distance){
					distance=tempdistance;
					//closestpoint=point;
					otheraxes.add(point);
				}
			}

			allNormals.addAll(getUnitVectors(getNormals(otheraxes)));

			int hit=0;

			for(int u=0;u<allNormals.size();u++){

				double[] p1=getCircleProjection(circle,allNormals.get(u));
				double[] p2=getPolyProjection(poly,poly.getFaces(),allNormals.get(u));

				// check for containment
				if (circle.getCircle().contains(poly.getBounds()) || poly.getShape().contains(circle.getBounds())) {
					// get the overlap plus the distance from the minimum end points
					double mins = Math.abs(p1[0] - p2[0]);
					double maxs = Math.abs(p1[1] - p2[1]);
					// NOTE: depending on which is smaller you may need to
					// negate the separating axis!!
					if (mins < maxs) {
						overlap += mins;
					} else {
						overlap += maxs;
					}
				}


				if(!(overlaps(p1,p2)<0)){
					break; 
				}else{
					hit++;

					double o=(overlaps(p1,p2));

					if (Math.abs(o) < overlap) {
						overlap = Math.abs(o);
						double[] cacb = new double[]{(poly.getCenter()[0]-circle.getCenter()[0]),(poly.getCenter()[1]-circle.getCenter()[1])}; // the vector from ca to cb

						smallest=allNormals.get(u);
						if (getDotProduct(cacb,smallest) < 0) {
							smallest=new double[]{-smallest[0],-smallest[1]};
						}
					}
				}

				//for a polygon every axis has to not have any overlap
				//for a rectangle only two axis
				if(hit==allNormals.size()-1){
					circle.moveBy(-overlap, smallest);
					//poly.moveBy(overlap/2, smallest);
					//System.out.println("c1 moved by: -"+overlap+" on "+smallest[0]+" "+smallest[1]);
					//System.out.println("c2 moved by: "+overlap+" on "+smallest[0]+" "+smallest[1]);
				}
			}

		}


		private void solveCircleCircle(Collider c1, Collider c2) {
			CircleCollider circle = (CircleCollider) c1;
			CircleCollider circle2 = (CircleCollider) c2;

			double[] circleCenter = circle.getCenter();
			double[] circleCenter2 = circle2.getCenter();

			//double[] mtv = new double[]{(circleCenter2[0]-circleCenter[0]),(circleCenter2[1]-circleCenter[0])};
			//mtv=getUnitVector(mtv);

			double dx = circleCenter2[0] - circleCenter[0];
			double dy = circleCenter2[1] - circleCenter[1];

			double radii = circle.getRadius() + circle2.getRadius();
			// Squared Distance squared "projections" of the circles
			//double overlap = Math.sqrt(((dx * dx + dy * dy)-(radii * radii)));

			if(((dx * dx + dy * dy)-(radii * radii)) < 0){

				//collision point
				double collisionPointX =((c1.getCenter()[0] * circle2.getRadius()) + (circle2.getCenter()[0] * circle.getRadius()))/ (circle.getRadius()+circle2.getRadius());

				double collisionPointY =((c1.getCenter()[1] * circle2.getRadius()) + (circle2.getCenter()[1] * circle.getRadius()))/ (circle.getRadius()+circle2.getRadius());

				double[] mtv=new double[]{(circleCenter2[0]-collisionPointX),(circleCenter2[1]-collisionPointY)};
				//double mtvx=circleCenter2[0]-collisionPointX;
				//double mtvy=circleCenter2[1]-collisionPointY;
				mtv=getUnitVector(mtv);
				//System.out.println("circleCenter2[0] "+circleCenter2[0]+" collisionPointX "+collisionPointX+" collisionPointY "+collisionPointY);
				double[] p1 = getCircleProjection(c1,mtv);
				double[] p2 = getCircleProjection(c2,mtv);

				//correct
				double overlaps=overlaps(p1,p2);

				if (circle.getCircle().contains(circle2.getCircle().getBounds()) || circle2.getCircle().contains(circle.getBounds())) {
					// get the overlap plus the distance from the minimum end points
					double mins = Math.abs(p1[0] - p2[0]);
					double maxs = Math.abs(p1[1] - p2[1]);
					// NOTE: depending on which is smaller you may need to
					// negate the separating axis!!
					if (mins < maxs) {
						overlaps += mins;
					} else {
						overlaps += maxs;
					}
				}
				//System.out.println("hit!");
				c1.moveBy(overlaps, mtv);
				c2.moveBy(-overlaps, mtv);
				//System.out.println(overlaps);
				//System.out.println(mtv[0]+"/"+mtv[1]);
			}


		}


		public double[] getPolyProjection(Collider c, List<double[]> faces, double[] axis){
			double min = getDotProduct(axis,c.getPoints().get(0));
			double max = min;

			for(int x=1;x<faces.size();x++){
				double p = getDotProduct(axis,c.getPoints().get(x));

				if(p<min){min=p;}
				else if (p > max){max =p;}
			}
			return new double[]{(min), (max)};

		}

		public double[] getCircleProjection(Collider c,double[] axis){
			CircleCollider circle = (CircleCollider) c;

			double center = (c.getCenter()[0]*axis[0])+(c.getCenter()[1]*axis[1]);
			double min = center-circle.getRadius();
			double max = center+circle.getRadius();

			double p = getDotProduct(axis,c.getCenter());

			if(p<min){min=p;}
			else if (p > max){max =p;}
			//System.out.println(min+" "+max);
			return new double[]{(min),(max)};
		}

		public double overlaps( double[] p1, double[] p2){
			double p1x=p1[0]; double p1y=p1[1];
			double p1m=p1[0]+(p1[1]-p1[0])/2;
			double p2x=p2[0]; double p2y=p2[1];
			double p2m=p2[0]+(p2[1]-p2[0])/2;
			if(p1m<p2m){
				return p2x-p1y;
			}
			else{
				return p1x-p2y;
			}
		}

		private double[] getNormal(double[] face) {

			return new double[]{face[0],-face[1]};
		}

		private List<double[]> getNormals(List<double[]> faces) {

			List<double[]> normals=new ArrayList<double[]>();

			for(int j=0;j<faces.size();j++){
				double[] normal=new double[]{faces.get(j)[1],-faces.get(j)[0]};
				normals.add(normal);
			}

			return normals;
		}

		private double getDotProduct( double[] a, double[] b){
			return (a[0]*b[0])+(a[1]*b[1]);
		}

		private List<double[]> getUnitVectors( List<double[]> vectors){
			List<double[]> unitvectors = new ArrayList<double[]>();
			for(int k=0;k<vectors.size();k++){
				double length=Math.abs((Math.sqrt((vectors.get(k)[0]*vectors.get(k)[0])+(vectors.get(k)[1]*vectors.get(k)[1]))));
				double[] unitvector=(new double[]{(vectors.get(k)[0]/length),(vectors.get(k)[1]/length)});
				unitvectors.add(unitvector);
			}
			return unitvectors;
		}

		private double[] getUnitVector(double[] vector){
			double length=Math.abs((Math.sqrt((vector[0]*vector[0])+(vector[1]*vector[1]))));
			return new double[]{(vector[0]/length),(vector[1]/length)};
		}
	}


	public ExecutorService getExServ(){
		return hdpool;
	}


	
	
	public void testMethod(){
		System.out.println("hi"); 


		/*final CopyOnWriteArrayList<CopyOnWriteArrayList<collision.Collider>> allCollisions = new CopyOnWriteArrayList<CopyOnWriteArrayList<collision.Collider>>();
		for(int j=0;j<dataBase.getColliders().size();j++){
			final CopyOnWriteArrayList<collision.Collider> collidesWith = new CopyOnWriteArrayList<collision.Collider>();
			final Point p;
			System.out.println(j);
			rTree.getTree().nearestN(
					p=new Point((float)dataBase.getColliders().get(j).getCenter()[0],(float)dataBase.getColliders().get(j).getCenter()[1]),

					new TIntProcedure() {         // a procedure whose execute() method will be called with the results

						public boolean execute(int i) {
							//log.info("Rectangle " + i + " " + rects[i] + ", distance=" + rects[i].distance(p));

							System.out.println("Rectangle " + i + " "+dataBase.getColliders().get(i).getCenter()[0]+" "+dataBase.getColliders().get(i).getCenter()[1]+" " + rTree.getObjectRects().get(i) + ", distance=" +  rTree.getObjectRects().get(i).distance(p));
							collidesWith.add(dataBase.getColliders().get(i));


							return true;              // return true here to continue receiving results
						}
					},100,100);

			allCollisions.add(collidesWith);
		}

		for(CopyOnWriteArrayList<collision.Collider> cc : allCollisions){

			checkAndSolveCollisions(cc);
		}*/

		/*qt.Clear();
		//System.out.println("allColliderssize "+allColliders.size());
		for(final Map.Entry<Integer, collision.Collider> collider  : allColliders.entrySet()){
			qt.Insert(collider.getValue());
		}
		//RETRIEVING AND COMMENCING
		CopyOnWriteArrayList<Collider> tempreturnObjects = new CopyOnWriteArrayList<Collider>();

		for(final Map.Entry<Integer, collision.Collider> collider  : allColliders.entrySet()){

			qt.Get(tempreturnObjects, collider.getValue().getBounds());
			returnObjects.add((CopyOnWriteArrayList)tempreturnObjects.clone());
			tempreturnObjects.clear();
		}
		if(!returnObjects.isEmpty()){
			for(int i=0;i<returnObjects.size();i++){
				//hdpool.execute(new solveCollision(returnObjects.get(i)));
				new solveCollision(returnObjects.get(i));
			}
			returnObjects.clear();
		}

		//RTree realTree=rTree.getTree();
		//rTree.clearTree();
		//rTree.rebuildTree();
		/*for(final Map.Entry<Integer, collision.Collider> collider  : allColliders.entrySet()){
			rTree.addToRTree(collider.getValue());
		}*/
		/*for(final Map.Entry<Integer, collision.Collider> collider  : allColliders.entrySet()){
			//for(int j=0;j<concurrentHashMap.size();j++){
			//collision.Collider collider = concurrentHashMap.get(j);

			final CopyOnWriteArrayList<collision.Collider> collisions = new CopyOnWriteArrayList<collision.Collider>();
			final ArrayList<Integer> idList = new ArrayList<Integer>();
			final Point p;

			/*if(collider.getValue().hasMoved){
				rTree.deleteFromTree(collider.getValue());
				rTree.addToRTree(collider.getValue());
				collider.getValue().setMoved(false);
			}

			if(collider.getValue().getRTreeID()==0){
				rTree.getSi().nearestNUnsorted(
						p=new Point((float)collider.getValue().getCenter()[0],(float)collider.getValue().getCenter()[1]),

						new TIntProcedure() {         // a procedure whose execute() method will be called with the results

							public boolean execute(int i) {
								//System.out.println("Rectangle " + i + " "+dataBase.getColliders().get(i).getCenter()[0]+" "+dataBase.getColliders().get(i).getCenter()[1]+" " + rTree.getObjectRects().get(i) + ", distance=" +  rTree.getObjectRects().get(i).distance(p));

								System.out.println("Rectangle " + i + " "+(int)dataBase.getAllCollidersMap().get(i).getCenter()[0]+" "+(int)dataBase.getAllCollidersMap().get(i).getCenter()[1]+" " + ", distance=" + dataBase.getAllCollidersMap().get(i).getrTreeBounds().distance(p) );
								//rTree.getObjectRects().get(i).distance(p)

								collisions.add(collider.getValue());
								//idList.add(i);
								return true;              // return true here to continue receiving results
							}
						},10,100);
			}else{
			rTree.getSi().nearestNUnsorted(
					p=new Point((float)collider.getValue().getCenter()[0],(float)collider.getValue().getCenter()[1]),

					new TIntProcedure() {         // a procedure whose execute() method will be called with the results

						public boolean execute(int i) {
							//System.out.println("Rectangle " + i + " "+dataBase.getColliders().get(i).getCenter()[0]+" "+dataBase.getColliders().get(i).getCenter()[1]+" " + rTree.getObjectRects().get(i) + ", distance=" +  rTree.getObjectRects().get(i).distance(p));

							//System.out.println("Rectangle " + i + " "+(int)dataBase.getAllCollidersMap().get(i).getCenter()[0]+" "+(int)dataBase.getAllCollidersMap().get(i).getCenter()[1]+" " + ", distance=" +  rTree.getObjectRects().get(i).distance(p));

							collisions.add(collider.getValue());
							//idList.add(i);
							return true;              // return true here to continue receiving results
						}
					},10,100);

			new solveCollision(collisions);
			//if(!collisions.isEmpty()){

			//}
			}
		}
	}
*/
}
