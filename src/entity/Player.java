package entity;

public class Player extends Entity {

	private static Player player = new Player();
	
	private Player(){
		
	}
	
	public Player getInstance() {
		return player;
	}

	@Override
	public void runCollisionChecking() {

	}
}
