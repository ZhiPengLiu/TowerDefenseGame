package towers;

import critters.Critter;
import game.Player;

public class Tower {	
	
	protected enum type{
		FREEZE, SNIPER, NORMAL
	}
	protected int buyingCost = 100;
	protected int refundValue = 90;
	protected double range = 100;
	protected int power = 4;
	protected int rateofFire = 2000;
	private int level =1;
	protected int upgradeCost = 200;
	private double xPos;
	private double yPos;
	protected boolean freezeTower = false;
	private Critter targetCritter;
	protected int reloadTime = 1;
	private long lastAttackTime = 0;
	private double angleOfRotation = 0;
	protected type towerType = type.NORMAL;
	//system time of last attack

	
	public Tower(int buyingCost, int refundValue, double range, int power, int rateofFire, 
			int level, int upgradeCost, double xPos, double yPos, boolean isFreezeTower) {
		// TODO Auto-generated constructor stub
		this.buyingCost = buyingCost;
		this.refundValue = refundValue;
		this.range = range;
		this.power = power;
		this.rateofFire = rateofFire;
		this.level = level;
		this.upgradeCost = upgradeCost;
		this.xPos = xPos;
		this.yPos = yPos;
		this.freezeTower = isFreezeTower;
		this.lastAttackTime = System.currentTimeMillis();
	}
	public Tower(double xPos, double yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.lastAttackTime = 0;
	}
	
	
	public boolean canAttack(){
		if( (System.currentTimeMillis()-lastAttackTime)/1000 >= reloadTime){

			return true;
		}
		else
			return false;
	}

	public void refundTower() { // credit of this tower's salvage value
											// is added to the user's account.
		Player.addCredits(refundValue);
	}

	public int getBuyingCost() { // getters and setters for the private
									// attributes of this object.
		return buyingCost;
	}

	public void setBuyingCost(int pBuyingCost) {
		buyingCost = pBuyingCost;
	}

	public double getRefundValue() {
		return refundValue;
	}

	public void setRefundValue(int pRefundValue) {
		refundValue = pRefundValue;
	}

	public void setTargetCritter(Critter c){
		targetCritter = c;
	}
	public Critter getTargetCritter(){
		return targetCritter;
	}
	

	public double getRotationAngleInDegrees(){
		if(targetCritter !=null)
			angleOfRotation = (180/Math.PI)*Math.atan2(targetCritter.getYLoc()-yPos, targetCritter.getXLoc()-xPos);
		return angleOfRotation;
	}
	
	public double getRange() {
		return range;
	}

	public void setRange(double pRange) {
		range = pRange;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int pPower) {
		power = pPower;
	}

	public double getRateofFire() {
		return rateofFire;
	}

	public void setRateofFire(int pRateofFire) {
		rateofFire = pRateofFire;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int pLevel) {
		level = pLevel;
	}

	public double getUpgradeCost() {
		return upgradeCost;
	}

	public void setUpgradeCost(int pUpgradeCost) {
		upgradeCost = pUpgradeCost;
	}
	
	public double getXLoc(){
		return xPos;
	}
	
	public double getYLoc(){
		return yPos;
	}
	
	public boolean isFreezeTower(){
		return freezeTower;
	}
	
	public long getTimeOfLastAttack(){
		return lastAttackTime;
	}
	public void setTimeOfLastAttack(long time){

		lastAttackTime = time;
	}
}
