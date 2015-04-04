package critters;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


abstract public class Critter{

	public enum type{GRUNT, SCOUT, ARMORED, TANK, BOSS};
	private double 		health;
	private double 		speed;
	private double 		modifier	= 0;
	private int			reward;
	private double 		armor;
	private float 		XLoc;
	private float		YLoc;
	private double		PrevXLoc;
	private double		PrevYLoc;
	private String		name;
	private boolean 	alive;
	public boolean		canMove = true;
	private int[][] 	locations;
	private int 		locationIncrementer = 0;
	private boolean 	visible = false;
	private boolean 	atEndPoint = false;
	protected type		critterType;
	private List<CrObserver> critterObservers;
	private ArrayList<DelayedDamage> damageDue = new ArrayList<DelayedDamage>();
	public enum direction {LEFT, RIGHT, UP, DOWN};

	direction critterDirection;

	//initialize critter at the start 
	public Critter(int[][] pLocations, double pHealth, double pArmor, double pSpeed, int pReward, String pName){
		health =pHealth;
		armor = pArmor;
		speed =pSpeed;
		reward = pReward;
		name = pName;
		XLoc = pLocations[0][0];
		YLoc = pLocations[0][1];
		alive = true;
		locations = pLocations;
		critterObservers = new ArrayList<CrObserver>();
		critterType = type.GRUNT;

	}


	
	public void move(){

		if(locationIncrementer ==0)
		{
			visible = true;
		}
		takeDueDamage();
		try{

			if(!(XLoc>locations[locationIncrementer+1][0]-speed&&XLoc<locations[locationIncrementer+1][0]+speed) ){
				if(XLoc<=locations[locationIncrementer+1][0]){
					XLoc += speed;
					critterDirection = direction.RIGHT;
				}
				else if(XLoc>=locations[locationIncrementer+1][0])
				{
					XLoc -= speed;
					critterDirection = direction.LEFT;
				}
			}
			else if(!(YLoc>=locations[locationIncrementer+1][1]-speed&&YLoc<=locations[locationIncrementer+1][1]+speed) ){
				if(YLoc<=locations[locationIncrementer+1][1]){
					YLoc += speed;
					critterDirection = direction.DOWN;
				}
				else if(YLoc>=locations[locationIncrementer+1][1]){
					YLoc -= speed;
					critterDirection = direction.UP;
				}
			}
			else{
				locationIncrementer++;
			}


		}
		catch(IndexOutOfBoundsException e){
			visible=false;
			atEndPoint = true;
		}
	}

	private void takeDueDamage(){
		ArrayList<DelayedDamage> dToRemove = new ArrayList<DelayedDamage>();
		for(DelayedDamage d: damageDue){
			if (System.currentTimeMillis()>d.timeOfDamage){
				takeDamage(d.damage);
				dToRemove.add(d);
			}
		}
		for(DelayedDamage d: dToRemove){
			damageDue.remove(d);
		}
	}
	public void hitCritter(double damage, long delay){
		damageDue.add(new DelayedDamage(System.currentTimeMillis()+delay, damage));
	}

	public void takeDamage(double damage){
		health = health - damage/armor;
		if(health <= 0){
			alive = false;
			visible = false;
		}
		
		//every time the critter takes damage, tell the observers

		notifyObservers();

	}

	
	
	//this method updates the location of both previous location variables each time the critter moves
	public void updateLocation(){
		PrevXLoc = XLoc;
		PrevYLoc = YLoc;
	}

	//method checks if critter has reach endpoint
	public boolean isCritterAtEndpoint(int[] exit){
		if(XLoc == exit[0] && YLoc == exit[1])
			return true;

		return false;
	}

	//observer classes
	public void addObserver(CrObserver o){
		if(o != null)
			if(!critterObservers.contains(o))
				critterObservers.add(o);


	}

	public void notifyObservers(){
		for(CrObserver o :critterObservers){
			o.update();
		}
	}

	public void removeObserver(CrObserver o){
		critterObservers.remove(o);
	}


	//Getters and Setters

	
	
	
	
	public String getName() {
		return name;
	}
	
	

	public boolean isAtEndPoint() {
		return atEndPoint;
	}



	public double getSpeed() {
		return speed;
	}

	public direction getCritterDirection() {
		return critterDirection;
	}

	public boolean isVisible() {
		return visible;
	}


	public double getHealth() {
		return health;
	}


	public float getXLoc() {
		return XLoc;
	}


	public float getYLoc() {
		return YLoc;
	}
	

	public double getModifier() {
		return modifier;
	}


	public int getReward() {
		return reward;
	}


	public boolean isAlive() {
		return alive;
	}


	public boolean CanMove() {
		return canMove;
	}
	

	public type getType(){
		return critterType;
	}



}

class DelayedDamage{
	long timeOfDamage;
	double damage;
	
	public DelayedDamage(long time, double damage){
		this.timeOfDamage = time;
		this.damage = damage;
	}
	
}
