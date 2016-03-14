package entity;

import java.util.List;

public abstract class Entity {
	
	protected int xPos;
	protected int yPos;
	
	protected List<Entity> potentialCollisions;
	
	public void checkCollision(Entity ent) {
		potentialCollisions.add(ent);
	}
	
	public abstract void runCollisionChecking();

}
