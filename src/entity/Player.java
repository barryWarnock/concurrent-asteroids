package entity;

public class Player extends Entity {

	private static Player player = new Player();
	protected static int score;
	protected static int lives = 3;

	private Player(){
	}
	
	public Player getInstance() {
		return player;
	}

	public Player initialize(int xLocation, int yLocation) {
		this.xPos = xLocation;
		this.yPos = yLocation;
		return player;
	}


	/**
	 * TODO complete this, this should possibly be done in Enitity.
	 */
	public void runCollisionChecking() {

	}


	public void updateLocationAndMomentum() {
		//TODO
	}

	public static int getScore(){
		return score;
	}
}
