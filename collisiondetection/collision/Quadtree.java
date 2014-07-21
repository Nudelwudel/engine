package collision;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import database.EngineDatabase;


public class Quadtree {

	private final int MAX_OBJECTS=1;
	private final int MAX_LEVELS=3;

	private final int level;
	private final List<Collider> objects;
	private final Rectangle bounds;
	private final Quadtree[] nodes;
	private EngineDatabase db;

	public Quadtree(final int pLevel,final Rectangle pBounds,database.EngineDatabase db) {
		this.db=db;
		this.level=pLevel;
		this.objects=new ArrayList<>();
		this.bounds=pBounds;
		this.nodes=new Quadtree[4];
	}

	/*
	 * Clears the quadtree
	 */
	public void clear() {
		this.objects.clear();
		for (int i = 0; i < this.nodes.length; i++) {
			if (this.nodes[i] != null) {
				this.nodes[i].clear();
				this.nodes[i] = null;
			}
		}
	}

	/* 
	 * Splits the node into 4 subnodes
	 */
	private void split() {
		final int subWidth = (int)(this.bounds.getWidth() / 2);
		final int subHeight = (int)(this.bounds.getHeight() / 2);
		final int x = (int)this.bounds.getX();
		final int y = (int)this.bounds.getY();

		this.nodes[0] = new Quadtree(this.level+1, new Rectangle(x + subWidth, y, subWidth, subHeight),db);
		this.nodes[1] = new Quadtree(this.level+1, new Rectangle(x, y, subWidth, subHeight),db);
		this.nodes[2] = new Quadtree(this.level+1, new Rectangle(x, y + subHeight, subWidth, subHeight),db);
		this.nodes[3] = new Quadtree(this.level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight),db);
	}


	/*
	 * Determine which node the object belongs to. -1 means
	 * object cannot completely fit within a child node and is part
	 * of the parent node
	 */
	private int getIndex(final Collider sc) {
		int index = -1;
		final double verticalMidpoint = this.bounds.getX() + (this.bounds.getWidth() / 2);
		final double horizontalMidpoint = this.bounds.getY() + (this.bounds.getHeight() / 2);
		// Object can completely fit within the top quadrants
		final boolean topQuadrant = (sc.getBounds().getY() < horizontalMidpoint && sc.getBounds().getY() + sc.getBounds().getHeight() < horizontalMidpoint);
		// Object can completely fit within the bottom quadrants
		final boolean bottomQuadrant = (sc.getBounds().getY() > horizontalMidpoint);

		// Object can completely fit within the left quadrants
		if (sc.getBounds().getX() < verticalMidpoint && sc.getBounds().getX() + sc.getBounds().getWidth() < verticalMidpoint) {
			if (topQuadrant) {
				index = 1;
			}
			else if (bottomQuadrant) {
				index = 2;
			}
		}
		// Object can completely fit within the right quadrants
		else if (sc.getBounds().getX() > verticalMidpoint) {
			if (topQuadrant) {
				index = 0;
			}
			else if (bottomQuadrant) {
				index = 3;
			}
		}
		return index;
	}

	private List<Integer> getIndexes(Collider sc)
	{
		List<Integer> indexes = new ArrayList<Integer>();
		double verticalMidpoint = bounds.x + (bounds.width / 2);
		double horizontalMidpoint = bounds.y + (bounds.height / 2);
		boolean topQuadrant = sc.getBounds().getY() >= horizontalMidpoint;
		boolean bottomQuadrant = (sc.getBounds().getY() - sc.getBounds().getHeight()) <= horizontalMidpoint;
		boolean topAndBottomQuadrant = sc.getBounds().getY() + sc.getBounds().getHeight() + 1 >= horizontalMidpoint && sc.getBounds().getY() + 1 <= horizontalMidpoint;
		if(topAndBottomQuadrant)
		{
			topQuadrant = false;
			bottomQuadrant = false;
		}
		// Check if object is in left and right quad
		if(sc.getBounds().getX() + sc.getBounds().getWidth() + 1 >= verticalMidpoint && sc.getBounds().getX() -1 <= verticalMidpoint)
		{
			if(topQuadrant)
			{
				indexes.add(2);
				indexes.add(3);
			}
			else if(bottomQuadrant)
			{
				indexes.add(0);
				indexes.add(1);
			}
			else if(topAndBottomQuadrant)
			{
				indexes.add(0);
				indexes.add(1);
				indexes.add(2);
				indexes.add(3);
			}
		}
		// Check if object is in just right quad
		else if(sc.getBounds().getX() + 1 >= verticalMidpoint)
		{
			if(topQuadrant)
			{
				indexes.add(3);
			}
			else if(bottomQuadrant)
			{
				indexes.add(0);
			}
			else if(topAndBottomQuadrant)
			{
				indexes.add(3);
				indexes.add(0);
			}
		}
		// Check if object is in just left quad
		else if(sc.getBounds().getX() - sc.getBounds().getWidth() <= verticalMidpoint)
		{
			if(topQuadrant)
			{
				indexes.add(2);
			}
			else if(bottomQuadrant)
			{
				indexes.add(1);
			}
			else if(topAndBottomQuadrant)
			{
				indexes.add(2);
				indexes.add(1);
			}
		}
		else
		{
			indexes.add(-1);
		}
		return indexes;
	}

	/*
	 * Insert the object into the quadtree. If the node
	 * exceeds the capacity, it will split and add all
	 * objects to their corresponding nodes.
	 */
	public void insert(final Collider sc) {
		if (this.nodes[0] != null) {

			//final int index = this.getIndex(sc);
			List<Integer> indexes = getIndexes(sc);

			for(int i=0;i<indexes.size();i++){
				int index = indexes.get(i);

				if(index != -1){
					nodes[index].insert(sc);
					return;
				}
			}
		}

		this.objects.add(sc);

		if (this.objects.size() > this.MAX_OBJECTS && this.level < this.MAX_LEVELS) {
			if (this.nodes[0] == null) {
				this.split();
			}

			int i = 0;
			while (i < this.objects.size()) {
				//final int index = this.getIndex(this.objects.get(i));
				List<Integer> indexes = getIndexes(sc);

				for(int j=0;j<indexes.size();j++){
					int index = indexes.get(j);
					
					if (index != -1)
					{
						nodes[index].insert(sc);
						//objects.remove(sc);
					}
					else{
						i++;
					}
				}
			}
		}
	}




	/*
	 * Return all objects that could collide with the given object
	 */
	public List<Collider> retrieve(final List<Collider> tempreturnObjects,final Collider sc) {
		//final int index = this.getIndex(sc);
		List<Integer> indexes = getIndexes(sc);

		for(int i=0;i<indexes.size();i++){
			int index = indexes.get(i);

			if(index != -1 && nodes[0] != null)
			{
				nodes[index].retrieve(tempreturnObjects, sc);
			}	
			
			//tempreturnObjects.addAll(objects);
		}
		tempreturnObjects.addAll(this.objects);

		return tempreturnObjects;
	}

	public void remove(Collider collider) {
		this.objects.remove(collider);

	}
}
