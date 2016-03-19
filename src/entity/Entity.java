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

	protected int width;
	protected int height;
	
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

	public int get_x() {
		return xPos;
	}

	public void set_x(int xPos) {
		this.xPos = xPos;
	}

	public int get_y() {
		return yPos;
	}

	public void set_y(int yPos) {
		this.yPos = yPos;
	}

	public int get_width() {
		return width;
	}

	public void set_width(int width) {
		this.width = width;
	}

	public int get_height() {
		return height;
	}

	public void set_height(int height) {
		this.height = height;
	}
}
