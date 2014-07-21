package entities;



import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import ai.AIBehavior;
import collision.CircleCollider;

public class Actor extends CircleCollider{

	database.EngineDatabase dataBase;

	boolean alive = true;

	String name="";
	String side="";

	int health=100;
	int armor=0;
	int stamina=0;
	int morale=0;

	String facing="";

	boolean marked=false;


	Line2D.Double sightpath = new Line2D.Double();
	Rectangle activeWaypoint = new Rectangle();
	Arc2D.Double sightArc = new Arc2D.Double(Arc2D.PIE);
	Ellipse2D.Double awarenessCircle = new Ellipse2D.Double();
	BufferedImage image=null;


	int combatstatus;

	String role="";
	int range;
	
	Actor activeenemy;
	ArrayList<Actor> unitsInSight = new ArrayList<Actor>();
	ArrayList<Actor> surroundingactors = new ArrayList<Actor>();
	ArrayList<Actor> enemylist = new ArrayList<Actor>();

	String playercommand="";
	ai.AIBehavior ai;

	graphics.GraphicsStorage gs;


	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	Random rnd = new Random();


	boolean markedforcollision=true;
	double thistick=0;

	private String currentactivity;

	public Actor(int posx,int posy,double width,String side,database.EngineDatabase dataBase){
		super(posx-2,posy+10,15);
		
		setDatabase(dataBase);
		
		setGraphicsStorage(dataBase.getGs());
		setImage(gs.getGraphic("", ""));
		setName(randomString(6));

		setSide(side);
		super.setDirection(randInt(0,360));
		setFaced(getDirection());

		setMorale(100);

		//setAIBehavior(new SandboxAIBehavior(this,model));

	}

	//////////////////////////////
	//////////////////////////////
	//INITIAL STUFF

	public void setName(final String newname){name=newname;}
	public String getName(){return name;}

	public String getSide(){return side;}
	public void setSide(final String newside){side=newside;}

	/////////////////////////
	/////	
	/////	A T T R I B U T E S
	/////
	/////////////////////////

	public void setHealth(final int h){
		health=h;

		if(health<=0){setDead();}
	}
	public double getHealth(){return health;}

	public boolean isAlive(){return alive;}
	public void setDead(){alive=false;}

	public void setMorale(int m) {morale=m;}
	public int getMorale(){return morale;}

	public void setArmor(int a){armor=a;}
	public double getArmor(){return armor;}

	/////////////////////////
	/////	
	/////	P O S I T I O N A L
	/////
	/////////////////////////


	/*public double[] getInterpolatedPosition(final double interpolation) {
		return new double[]{position[0]+(1*interpolation),position[1]-(1*interpolation)};
	}*/

	/////////////////////////
	/////	
	/////	C O M B A T   S T A T U S
	/////
	/////////////////////////

	public void setCombatStatus(final int cmbt){combatstatus=cmbt;}
	public int getCombatStatus(){return combatstatus;}

	/*public ArrayList<Actor> getSurroundingActors(){
		return surroundingactors;
	}*/

	//public ArrayList<Actor> getUnitsInSight() {return unitsInSight;}

	public void setRole(String type) {
		switch (type){
			default: break;
		}
	}
	public String getRole() {
		return role;
	}
	//////////////////////////////
	//////////////////////////////
	//COMMAND STUFF
	public String getPlayerCommand() {return playercommand;}

	public void addPlayerCommand(final String string) {playercommand=string;}

	public String getCurrentActivity(){
		if(!currentactivity.equals("")){
			return currentactivity;
		}else{
			return "";
		}
	}
	public void setCurrentActivity(final String cmd){
		currentactivity=cmd;
	}


	/////////////////////////
	/////	
	/////	C O M M A N D S
	/////
	/////////////////////////
	public void moveInDirection(){
		//double futurex=getX()+(0.5*(double)Math.cos((getDirection()-90)*Math.PI/180));
		//double futurey=getY()+(0.5*(double)Math.sin((getDirection()-90)*Math.PI/180));
		updatePosition(getX()+(0.5*Math.cos((getDirection()-90)*Math.PI/180)),getY()+(0.5*Math.sin((getDirection()-90)*Math.PI/180)),0);
	}
	
	@Override
	public void moveBy(final double a,final double[] b){
		updatePosition(getX()+(a*b[0]),getY()+(a*b[1]),0);
	}
	
	/*public void moveInDirection(final double interpolation){
		final double dir=getDirection();
		setPosition(new double[]{getX()+(0.33f*(double)Math.cos((dir-90)*Math.PI/180)),
		getY()+(0.33f*(double)Math.sin((dir-90)*Math.PI/180))});
	}*/


	//dataBase.getAudio().quickPlay(getGun().getSndfile(),false,new SimpleVector(getX(),getY(),5),SoundSystemConfig.ATTENUATION_ROLLOFF,SoundSystemConfig.getDefaultRolloff() );

	/////////////////////////
	/////	
	/////	W E A P O N   R E L A T E D
	/////
	/////////////////////////

	/*public Weapon getGun() {
		return gun;
	}

	public void setGun(final Weapon newgun) {
		gun = newgun;
	}

	public Magazine getMag() {
		return mag;
	}

	public void setMag(final Magazine newmag) {
		mag = newmag;
	}
	public void reload(){
		gun.insertMagazine(mag);
	}*/
	public void setRange(final int r){
		range=r;
	}
	public int getRange(){
		return range;
	}

	 

	/////////////////////////
	/////	
	/////	A I   A N D   E N E M Y   R E L A T E D
	/////
	/////////////////////////
	
	public AIBehavior getAIBehavior() {return ai;}
	public void setAIBehavior(AIBehavior AIBehavior) {ai=AIBehavior;}

	public void centerOnEnemy(){
		setDirection(theta(getActiveEnemy().getY(),getActiveEnemy().getX())*180/Math.PI-90);//toDegrees-90Offset
		//System.out.println(getName()+"s direction is now "+(getDirection())+" looking to the "+getFaced());
	}

	public Actor getActiveEnemy(){
		return activeenemy;
	}
	public void setActiveEnemy(Actor newactiveenemy){
		activeenemy=newactiveenemy;
	}
	public void deleteActiveEnemy() {
		activeenemy=null;
	}
	public void setEnemyList(ArrayList<Actor> newenemylist){
		enemylist=newenemylist;
	}
	public ArrayList<Actor> getEnemyList(){
		return enemylist;
	}
	////PATHING AND AREAS

	public Rectangle getActivewaypoint() {
		return activeWaypoint;
	}
	public void setActivewaypoint(int[] waypoint) {
		activeWaypoint.setBounds(waypoint[0]-25,waypoint[1]-25,50,50);
	}



	

	public String getFacing(){return facing;}

	public void setFaced(final double direction) {
		/*if(direction>=340&&direction<=360){facing="NORTH";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=0&&direction<=20){facing="NORTH";setImage(gs.getGraphic(getSide(),getFacing()));}
		if(direction>=20&&direction<=60){facing="NORTHEAST";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=60&&direction<=120){facing="EAST";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=120&&direction<=160){facing="SOUTHEAST";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=160&&direction<=200){facing="SOUTH";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=200&&direction<=240){facing="SOUTHWEST";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=240&&direction<=300){facing="WEST";setImage(gs.getGraphic(getSide(), getFacing()));}
		if(direction>=300&&direction<=340){facing="NORTHWEST";setImage(gs.getGraphic(getSide(), getFacing()));}*/
	}

	/////////////////////////
	/////	
	/////	G E O M E T R Y
	/////
	/////////////////////////

	public Arc2D.Double getArc(){return sightArc;}

	@Override
	public Ellipse2D.Double getCircle(){return awarenessCircle;}

	public Line2D.Double getSightPath(){return sightpath;}


	public void updateCircle() {
		awarenessCircle.setFrame(getX()-getRange()/2, getY()-getRange()/2, getRange(), getRange());
	}

	public void updateArc() {
		sightArc.setFrame(getX()-(getRange()/2-5),getY()-(getRange()/2-5),getRange(),getRange());
		sightArc.setAngleStart(-getDirection()+30); sightArc.setAngleExtent(120);
	}

	/*public void updatePath(){
		sightpath.setLine(getBounds().x+3,getBounds().y+5, getActiveEnemy().getBounds().x+3,getActiveEnemy().getBounds().y+5);
	}*/



	/////////////////////////
	/////	
	/////	H E L P E R   M E T H O D S
	/////
	/////////////////////////
	public void setGraphicsStorage(graphics.GraphicsStorage gs) {this.gs=gs;}

	public void setDatabase(database.EngineDatabase ndataBase) { this.dataBase=ndataBase;}
	public database.EngineDatabase getDatabase() {return this.dataBase;}

	public static long GetTickCount() {return System.currentTimeMillis();}
	public void setCurrentTick(final double lastUpdateTime){thistick=lastUpdateTime;}
	public double getCurrentTick(){return thistick;}

	public static int randInt(final int min, final int max) {
		final Random rand = new Random();
		final int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	String randomString(final int length) {
		final StringBuilder sb = new StringBuilder(length);
		for( int i = 0; i < length; i++ )
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
	@Override
	public String toString(){return ("Name: "+getName()+"\nSide: "+getSide()+"\nHealth: "+getHealth()+"\nCombatStatus: "+getCombatStatus()+"\nWeapon: "+"noweapon"+"\nDirection: "+getDirection()+"\nFaced: "+getFacing()+"\nAmmo left: "+"noweaponnoammo"+"\nPosition X: "+getX()+" Y: "+getY()+"\nCommand: "+getCurrentActivity()+"\nSurrounding Actors: "+"nolist");}

	public BufferedImage getImage() {return image;}
	public void setImage(BufferedImage image) {this.image = image;}

	public boolean getMarked(){return marked;}
	public void setMarked(boolean m){marked=m;}

	public double theta(final double y,final double x){return Math.atan2(getCenter()[1]-y, getCenter()[0]-x);}

	//public int compareTo(Actor s) {return this.side.compareTo(s.getSide());}

	public boolean isMarkedforcollision() {return markedforcollision;}
	public void setMarkedforcollision(boolean markedforcollision) {this.markedforcollision = markedforcollision;}








}
