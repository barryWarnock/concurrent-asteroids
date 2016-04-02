package entity;

import game.Game;
import gui.Screen;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.lang.Runnable;

//TODO make entities wrap the screen.
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

		Game game = Game.getInstance();
        float screenWidth = game.getScreenWidth();
        float screenHeight = game.getScreenHeight();

        xPos = (xPos < 0) ? (screenWidth - width) : (xPos);
        xPos = (xPos + width > screenWidth) ? (0) : (xPos);

        yPos = (yPos < 0) ? (screenHeight - height) : (yPos);
        yPos = (yPos + height > screenHeight) ? (0) : (yPos);

        System.out.println("update");
    }

    /**
     * draws the entity to the screen buffer
     * @param screen the screen to draw to
     */
    public void draw(Screen screen) {
		//TODO get graphic size/2 to draw in the center
		screen.drawImage(sprite, (int) xPos, (int) yPos);
	}

    abstract public void die();
}
