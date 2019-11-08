package application.model;

import java.util.Random;
/*
 * Spaceship.java
 * 
 *  Creates a spaceship target object
 */
public class SpaceShip {
	/*
	 * The x and y coordinates of the target 
	 */
	private int x, y;
	/*
	 * A boolean check to respond to if target is hit
	 */
	private boolean hit;
	/*
	 * Determines hit size of target box
	 */
	private int hitSize;
	/*
	 * Random generator for movement
	 */
	Random generator = new Random();
	
	/*
	 * Default constructor
	 */
	public SpaceShip(){
		hit = false;
	}
	
	/*
	 * Constructor with parameters
	 * @param x
	 * 			x coordinate for object
	 * @param y
	 * 			y coordinate for object
	 */
	public SpaceShip(int x, int y){
		this.x = x;
		this.y = y;
		hit = false;
	}
	/*
	 * Move target in increment by input parameters
	 */
	public void move(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	public void randMove(){
		this.x += generator.nextInt(5) - 2; //move between -2 to 2
		this.y += generator.nextInt(5) - 2; //move between -2 to 2
	}
	/*
	 * Determine target hit
	 * @return hit
	 * 			boolean to confirm target hit
	 */
	public boolean isHit(){
		return hit;
	}
	
	/*
	 * Hit box : +-x and +-y of current coordinates
	 *  ___________
	 *  |         |
	 *  |   x,y   |
	 *  |         |
	 *  -----------
	 *  
	 *  if xAttack > x - hitSize && xAttack < x + hitSize && yAttack > y - hitSize && yAttack < y + hitSize
	 */
	
	/*
	 * Attack on target from player
	 * @param xAttack
	 * 				x coordinate of attack location
	 * @param yAttack
	 * 				y coordinate of attack location
	 */
	public void attack(int xAttack, int yAttack){
		if(xAttack > (x - hitSize) && xAttack < (x + hitSize) && yAttack > (y - hitSize) && yAttack < (y + hitSize)){
			hit =true;
		}
	}

}
