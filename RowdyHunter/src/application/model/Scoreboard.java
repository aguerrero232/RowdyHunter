package application.model;

/*
 * Scoreboard.java
 * 
 * Creates a score board object of keep track of player score
 * 
 */
public class Scoreboard {
	/*
	 * The current score in integer format
	 */
	private int score;
	/*
	 * The number of recorded hits
	 */
	private int hits;
	/*
	 * The number of recorded misses
	 */
	private int miss;
	
	/*
	 * Empty constructor
	 */
	public Scoreboard(){
		
	}
	/*
	 * Constructor with parameters
	 * @param score
	 * 				The passed in score value
	 * @param hits
	 * 				The passed in hit count
	 * @param miss
	 * 				The passed in miss count
	 */
	public Scoreboard(int score, int hits, int miss) {
		//super();
		this.score = score;
		this.hits = hits;
		this.miss = miss;
	}
	
	/*
	 * Get current score
	 */
	public int getScore() {
		return score;
	}
	/*
	 * Set the score to a new value
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/*
	 * Get the current number of hits
	 */
	public int getHits() {
		return hits;
	}
	/*
	 * Set a new amount for hits
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}
	/*
	 * Get the current amount of misses
	 */
	public int getMiss() {
		return miss;
	}
	/*
	 * Set the current amount of misses
	 */
	public void setMiss(int miss) {
		this.miss = miss;
	}
	
	
	
	
	
	

}
