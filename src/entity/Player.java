package entity;

public class Player extends Entity {

	private static Player player = new Player();

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

	@Override
	/**
	 * TODO complete this, this should possibly be done in Enitity.
	 */
	public void runCollisionChecking() {

	}

	@Override
	public void updateLocationAndMomentum() {
		//TODO
	}
}
