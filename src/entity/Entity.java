package entity;

public abstract class Entity {
	
	protected int xPos;
	protected int yPos;
	
	protected List<Entity> potentialCollisions;
	
	public void checkCollision(Entity ent) {
		potentialCollisions.add(ent);
	}
	
	public void runCollisionChecking();

}
