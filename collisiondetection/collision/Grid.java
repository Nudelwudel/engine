package collision;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Grid {

	ConcurrentHashMap<Integer,GridEntry> objects = new ConcurrentHashMap<Integer,GridEntry>(2000);
	
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>(2000);

	ArrayList<Node> nodes = new ArrayList<Node>(200);

	int columnWidth=77;
	int rowHeight=40;

	int rows=20;
	int columns=20;

	Node[][] nodeArray;
	
	int objectId=1;
	
	int counter=0;

	public Grid(int x,int y,int width, int height){
		nodeArray = new Node[rows][columns];
		int cellId=0;

		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){

				Rectangle r = new Rectangle(x+columnWidth*i,y+rowHeight*j,columnWidth,rowHeight);

				Node n = new Node(r,j,i,cellId);

				nodeArray[j][i]=n;
				nodes.add(n);

				rectangles.add(r);
				
				cellId++;
			}
			
		}
	}



	public ConcurrentHashMap<Integer,GridEntry> getObjects() {
		return objects;
	}

	public ArrayList<Rectangle> getRectangles() {
		return rectangles;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void addObject(collision.Collider c){
		Integer nodeIndex=0;

		//traverse columns first?
		for(int i=0;i<columns;i++){
			for(int j=0;j<rows;j++){
				Node tempNode = nodeArray[j][i];
				if(tempNode.intersects(c)){
					GridEntry g = new GridEntry(objectId,c);
					g.getNodeIndices().add(nodeIndex);
					c.setGridID(objectId);
					
					objects.put(objectId,g);
					
					//tempNode.add(g);
					
					objectId++;
				}
			}
			nodeIndex++;
		}
	}

	public void deleteObject(Integer id){
		objects.remove(id);
	}

	public void updateEntry(collision.Collider c){
		Integer nodeIndex=0;
		GridEntry g = objects.get(c.getGridID());
		
		ArrayList<Integer> newnodes=findObject(c);
		
		for(Integer i : newnodes){
			if(!g.getNodeIndices().contains(i)){
				nodes.get(i).add(g);
			}
		}
		for(Integer i : g.getNodeIndices()){
			if(!newnodes.contains(i)){
				nodes.get(i).delete(c);
			}
		}
		
		g.setNodeIndices(newnodes);
		
		
		/*for(Node n : nodes){
			if(n.intersects(c)){
				if(!c.getNodeIndices().contains(nodeIndex)){
					c.getNodeIndices().add(nodeIndex);
					n.add(c);
				}
			}else{
				c.getNodeIndices().remove(nodeIndex);
				n.delete(c);
			}

			nodeIndex++;
		}*/
	}


	public ArrayList<Integer> findObject(collision.Collider c){
		long findCheck1 = System.currentTimeMillis();
		ArrayList<Integer> objNodes = new ArrayList<Integer>(4);

		//find column and row
		int columnStart = 0;
		int columnEnd = 0;

		int rowStart=0;
		int rowEnd=0;

		columnStart = (int)((c.getBounds().getMinX()/columnWidth)%columns);
		columnEnd = (int)((c.getBounds().getMaxX()/columnWidth)%columns);
		rowStart = (int)((c.getBounds().getMinY()/rowHeight)%rows);
		rowEnd=(int)((c.getBounds().getMaxY()/rowHeight)%rows);
		

		if(columnStart==columnEnd&&rowStart==rowEnd){
			int cell=columnStart*columns+rowStart;
			objNodes.add(nodes.get(cell).getCellId());
		}
		else{
			for(int i=columnStart;i<=columnEnd;i++){
				for(int j=rowStart;j<=rowEnd;j++){
					objNodes.add(nodeArray[j][i].getCellId());
				}
			}
		}
		long findCheck2 = System.currentTimeMillis();
		if(counter%1000==0){
			//System.out.println("finding object took: "+(findCheck2-findCheck1)+"ms.");
		}
		return objNodes;
	}

	public CopyOnWriteArrayList<Collider> getNearestObjects(collision.Collider c){
		CopyOnWriteArrayList<collision.Collider> colliders = new CopyOnWriteArrayList<collision.Collider>();

		GridEntry g = objects.get(c.getGridID());
		
		for(Integer nodeId : g.getNodeIndices()){
			for(final Map.Entry<Integer, GridEntry> collidingWith  : nodes.get(nodeId).getObjects().entrySet()){
				colliders.add(collidingWith.getValue().getCollider());
			}
		}
		//System.out.println("colliders: "+colliders.size());
		return colliders;
	}











}
