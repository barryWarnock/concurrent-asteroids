package main;
/** This is a singleton class which holds the score of the game.
 * This may be merged into Screen.java at a later date.
 *
 *
 */
public class ScoreHandler {

	private int score;
	private ScoreHandler instance = new ScoreHandler();

	private ScoreHandler() {}
	
	public ScoreHandler getInstance() {
		return instance;
	}
	public void intialize() {
		score = 0;
	}

	public void update(int value) {
		score += value;
	}

	public int getScore() {
		return score;
	}

}
