package application.model;

/*
 * Player.java
 * 
 * Creates a player object
 * A player will have a current score based on how how well they have hit targets. 
 * Lives will be an indicator for how many times they can fail to hit a target.
 * When lives are expended the player will see a game over.
 */
public class Player {
	/*
	 * The player's score
	 */
	private int score;
	/*
	 * The player's lives
	 */
	private int lives;
	/*
	 * Default constructor
	 */
	public Player(){
		lives = 10;
		score = 0;
	}
	
	/*
	 * Constructor with parameters
	 * @param score
	 * 				passed in player score starting amount
	 * @param lives
	 * 				passed in player life count
	 */
	public Player(int score, int lives) {
		super();
		this.score = score;
		this.lives = lives;
	}

	

	/*
	 * Add to the player's current score
	 */
	public void addScore(int score){
		this.score += score;
	}
	
	
	
	

}
