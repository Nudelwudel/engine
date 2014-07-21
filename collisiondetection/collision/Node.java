package collision;

import java.awt.Rectangle;
import java.util.concurrent.ConcurrentHashMap;

public class Node {
	
	ConcurrentHashMap<Integer,GridEntry> objects = new ConcurrentHashMap<Integer,GridEntry>();
	int row=0;
	int column=0;
	int cellId;
	Rectangle rect;
	
	public Node(Rectangle rect,int row, int column,int cellId){
		this.cellId=cellId;
		this.row=row;
		this.column=column;
		this.rect=rect;
		System.out.println(cellId);
	}
	
	public void add(GridEntry g){
		objects.put(g.getID(),g);
	}
	
	public void delete(collision.Collider c){
		objects.remove(c.getGridID());
	}
	
	public ConcurrentHashMap<Integer,GridEntry> getObjects(){
		return objects;
	}
	
	public boolean intersects(collision.Collider c){
		return rect.intersects(c.getBounds());
	}
	
	public Rectangle getRect(){
		return rect;
	}

	public int getCellId() {
		return cellId;
	}
	
	@Override
	public String toString(){
		return "Column: "+column+" Row: "+row;
	}
}
