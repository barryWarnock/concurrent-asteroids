package entity;

import java.util.List;

public abstract class Entity {
	
	protected int xPos;
	protected int yPos;
	protected int xSpeed;
	protected int ySpeed;

	public Entity() {
		xPos = 0;
		yPos = 0;
		xSpeed = 0;
		ySpeed = 0;
	}
	
	protected List<Entity> potentialCollisions;
	
	public void checkCollision(Entity ent) {
		potentialCollisions.add(ent);
	}
	
	public abstract void runCollisionChecking();

	public abstract void updateLocationAndMomentum();

	/**
	 * This is a very naive
	 * @return Asteroid momentum
	 */
	protected int momentum() {
		return (xSpeed + ySpeed);
	}

}
