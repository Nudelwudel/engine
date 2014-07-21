package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class PlayerKeyListener implements KeyListener{

	collision.Collider player;


	// 8 directions
	double[] moveUp = new double[]{0,-1};
	double[] moveDown = new double[]{0,1};
	double[] moveLeft = new double[]{1,0};
	double[] moveRight = new double[]{-1,0};
	double[] moveUpLeft =getUnitVector(new double[] {moveUp[0]+moveLeft[0],moveUp[1]+moveLeft[1]});
	double[] moveUpRight = getUnitVector(new double[] {moveUp[0]+moveRight[0],moveUp[1]+moveRight[1]});
	double[] moveDownLeft = getUnitVector(new double[] {moveDown[0]+moveLeft[0],moveDown[1]+moveLeft[1]});
	double[] moveDownRight = getUnitVector(new double[] {moveDown[0]+moveRight[0],moveDown[1]+moveRight[1]});

	int speed=3;


	private final Set<Integer> keysheld = new HashSet<Integer>();

	public PlayerKeyListener() {
	}

	@Override
	public void keyTyped(final KeyEvent e) {
	}

	public Set<Integer> getKeysHeld(){
		return this.keysheld;
	}
	@Override
	public void keyPressed(final KeyEvent e) {
		this.keysheld.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		this.keysheld.remove(e.getKeyCode());
	}

	public void decideAction(){

		/*if(keysheld.size()>1){
			if(keysheld.contains(87)&&keysheld.contains(68)){player.moveBy(player.getSpeed(),moveUpLeft);}
			if(keysheld.contains(87)&&keysheld.contains(65)){player.moveBy(player.getSpeed(),moveUpRight);}
			if(keysheld.contains(83)&&keysheld.contains(68)){player.moveBy(player.getSpeed(),moveDownLeft);}
			if(keysheld.contains(83)&&keysheld.contains(65)){player.moveBy(player.getSpeed(),moveDownRight);}
			//if(keysheld.contains(32)){player.moveBy(1,new double[]{1,0});rects.getRect2().moveBy(1,new double[]{-1,0});}
		}
		else{
			if(keysheld.contains(87)){player.moveBy(player.getSpeed(),moveUp);}
			if(keysheld.contains(68)){player.moveBy(player.getSpeed(),moveLeft);}
			if(keysheld.contains(83)){player.moveBy(player.getSpeed(),moveDown);}
			if(keysheld.contains(65)){player.moveBy(player.getSpeed(),moveRight);}
			//if(keysheld.contains(32)){player.moveBy(1,new double[]{1,1});rects.getRect2().moveBy(1,new double[]{-1,-1});}
		}*/
		//FOR DEBUGGING PURPOSES
		if(player!=null){
			int x=0;int y=0;
			
			if(keysheld.size()>1){

				if(keysheld.contains(87)&&keysheld.contains(68)){player.moveBy(speed,moveUpLeft);}
				else if(keysheld.contains(87)&&keysheld.contains(65)){player.moveBy(speed,moveUpRight);}
				else if(keysheld.contains(83)&&keysheld.contains(68)){player.moveBy(speed,moveDownLeft);}
				else if(keysheld.contains(83)&&keysheld.contains(65)){player.moveBy(speed,moveDownRight);}
			}
			else {
				//System.out.println(player.getX()+" "+player.getY());
				if(keysheld.contains(87)){player.moveBy(speed,moveUp);}
				else if(keysheld.contains(68)){player.moveBy(speed,moveLeft);}
				else if(keysheld.contains(83)){player.moveBy(speed,moveDown);}
				else if(keysheld.contains(65)){player.moveBy(speed,moveRight);}
			}
		}
		
		//System.out.println(keysheld);

	}
	public void setPlayer(collision.Collider c){
		this.player=c;
	}

	public collision.Collider getPlayer() {
		return player;
	}

	public double[] getUnitVector(double[] vector){
		double length=Math.abs(Math.sqrt((vector[0]*vector[0])+(vector[1]*vector[1])));
		return new double[]{(vector[0]/length),(vector[1]/length)};
	}

}
