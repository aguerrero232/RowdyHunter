package application.model;
/*
 * Gun.java
 * 
 * Creates a gun object
 */
public class Gun {

	/*
	 * Count for bullets
	 */
	private int bullets;
	
	/*
	 * Default constructor
	 */
	public Gun(){
		bullets = 3;
	}
	
	/*
	 * Reset ammo to starting amount
	 */
	public void reload(){
		bullets = 3;
	}
	
	/*
	 * Firing action
	 * Decrement ammo count
	 */
	public void fire(){
		if(bullets > 0){
			bullets--;
		}
	}

	/*
	 * Get the current number of bullets
	 */
	public int getBullets() {
		return bullets;
	}

	/*
	 * Set the bullet amount
	 */
	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
	
	
}
