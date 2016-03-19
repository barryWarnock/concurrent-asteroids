package entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
	
	protected int xPos;
	protected int yPos;

	protected int width;
	protected int height;

	protected int xSpeed;
	protected int ySpeed;

    protected List<Entity> collisions;

	public Entity() {
		xPos = 0;
		yPos = 0;
		xSpeed = 0;
		ySpeed = 0;

        collisions = new ArrayList<Entity>();
	}

    public boolean checkCollision(Entity other) {
        boolean doesCollide = false;

        if (((Math.abs(this.xPos - other.get_x()) * 2) < (this.width + other.get_width())) &&
           ((Math.abs(this.yPos - other.get_y()) * 2) < (this.height + other.get_height()))) {
            doesCollide = true;
        }

        return doesCollide;
    }

    public void reportCollision(Entity collided) {
        collisions.add(collided);
    }

    public void clearCollisions() {
        collisions = new ArrayList<Entity>();
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

    /**
     * updates the entities position
     */
    abstract public void update();

    /**
     * draws the entity to the screen buffer
     * @param buffer the buffer to draw to
     */
    abstract public void draw(Graphics buffer);

    abstract public void die();
}
