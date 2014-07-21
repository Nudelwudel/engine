package entities;

public class Vehicle extends collision.PolygonCollider {
	
	int speed;
	int torque;
	int mass;
	int direction=0;
	
	public Vehicle(int[] xpoints,int[] ypoints,int speed,int torque,int mass){
		super(xpoints,ypoints);
		this.speed=speed;
		this.torque=torque;
		this.mass=mass;
		System.out.println("new vehicle at: "+xpoints[0]+" "+ypoints[0]);
		
	}

	
	
	
	
	
	
	
	
	
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTorque() {
		return torque;
	}

	public void setTorque(int torque) {
		this.torque = torque;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}
	
	
	
	

}
