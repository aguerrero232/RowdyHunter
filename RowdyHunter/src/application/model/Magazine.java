package application.model;
/*
 * Magazine.java
 * 
 * A class to keep track of the magazine status for the game logic
 */
public class Magazine {

	/*
	 * The number of bullets in the magazine
	 */
	private int bulletCount;
	/*
	 * How many reloads there have been
	 */
	private int reloads;
	/*
	 * The maximum number of reloads allowed
	 */
	private final int reloadLimit = 10;
	/*
	 * Constructor with fields
	 * @param bulletCount
	 * 
	 * @param reloads
	 */
	public Magazine(int bulletCount, int reloads) {
		this.bulletCount = bulletCount;
		this.reloads = reloads;
	}
	/*
	 * Empty constructor
	 */
	public Magazine(){
		
	}
	/*
	 * Get current number of bullets
	 */
	public int getBulletCount() {
		return bulletCount;
	}
	/*
	 * Set the current number of bullets
	 */
	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}
	/*
	 * Get the current number of reloads
	 */
	public int getReloads() {
		return reloads;
	}
	/*
	 * Set the amount of reloads
	 */
	public void setReloads(int reloads) {
		this.reloads = reloads;
	}
	/*
	 * Get the reload limit
	 */
	public int getReloadLimit() {
		return reloadLimit;
	}
	/*
	 * Add to the reload count
	 */
	public void addReload(){
		reloads += 1;
	}
	/*
	 * Reload the magazine
	 */
	public void reload(){
		bulletCount = 3;
	}
	
	/*
	 * Decrement one bullet from magazine
	 */
	public void fire(){
		bulletCount -= 1;
	}
	
	/*
	 * Check to see if player has reloaded past the limit
	 */
	public boolean reloadsExceeded(){
		if(reloads >= reloadLimit){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
}
