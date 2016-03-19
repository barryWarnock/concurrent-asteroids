package entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
	
	protected double xPos;
	protected double yPos;

	protected int width;
	protected int height;

	protected double xSpeed;
	protected double ySpeed;

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

	public double get_x() {
		return xPos;
	}

	public void set_x(double xPos) {
		this.xPos = xPos;
	}

	public double get_y() {
		return yPos;
	}

	public void set_y(double yPos) {
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
