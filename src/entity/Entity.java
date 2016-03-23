package entity;

import gui.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.lang.Runnable;

public abstract class Entity implements Runnable {
	
	protected double xPos;
	protected double yPos;

	protected int width;
	protected int height;

	protected double xSpeed;
	protected double ySpeed;

	protected BufferedImage sprite;

    protected List<Entity> collisions;

	public Entity() {
		xPos = 0;
		yPos = 0;
		xSpeed = 0;
		ySpeed = 0;

        collisions = new ArrayList<>();
	}

    public void setPos(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void setSpeed(int xVel, int yVel) {
        this.xSpeed = xVel;
        this.ySpeed = yVel;
    }

	@Override
    public void run() {
        update();
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
        collisions = new ArrayList<>();
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
    public void update() {
        xPos += xSpeed;
        yPos += ySpeed;
    }

    /**
     * draws the entity to the screen buffer
     * @param screen the screen to draw to
     */
    public void draw(Screen screen) {
		screen.drawImage(sprite, (int) xPos, (int) yPos);
	}

    abstract public void die();
}
