package collision;

import java.util.ArrayList;

public class GridEntry {
	
	collision.Collider collider;
	
	int id;
	
	ArrayList<Integer> nodeIndices = new ArrayList<Integer>();
	
	public GridEntry(int id, collision.Collider c){
		collider=c;
		this.id=id;
	}
	
	public ArrayList<Integer> getNodeIndices() {
		return nodeIndices;
	}
	
	public void setNodeIndices(ArrayList<Integer> newIndices) {
		this.nodeIndices=newIndices;
	}
	
	public int getID(){
		return id;
	}
	
	public collision.Collider getCollider(){
		return collider;
	}

}
