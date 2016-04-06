package entity;

import game.Game;
import gui.Screen;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Runnable {
	
	protected double xPos;
	protected double yPos;

	protected int width;
	protected int height;

	protected double xSpeed;
	protected double ySpeed;

	protected BufferedImage sprite;

    protected List<Entity> collisions;
	protected boolean collided = false;

	public Entity() {
		xPos = 0;
		yPos = 0;
		xSpeed = 0;
		ySpeed = 0;

        collisions = new ArrayList<>();
	}


	@Override
    public void run() {
        update();
    }

    public void checkCollision(Entity other) {
		collisions.add(other);
    }

	protected void checkCollision2(Entity other) {
		if (other.getClass() == this.getClass()) {
		} else if (
			(xPos < other.get_x() &&
			(xPos + width) > other.get_x())
			||
			(other.get_x() < xPos &&
			(other.get_x() + other.get_width()) > xPos)) {
			if ( //We know at this point the x/width overlaps
				(yPos < other.get_y() &&
				(yPos + height) > other.get_y())
				||
				(other.get_y() < yPos &&
				(other.get_y() + other.get_height()) > yPos)) {
					collided = true;
					other.set_collided(true);
				}
			}
	}

	protected boolean checkAllCollisions() {
		for(int i=0; collisions.size() > i; i++) {
			checkCollision2(collisions.get(i));
		}
		return collided;
	}
	public double get_x() {
		return xPos;
	}

	public double get_y() {
		return yPos;
	}

	public int get_width() {
		return width;
	}

	public int get_height() {
		return height;
	}

	public void set_collided(boolean collided) { this.collided = collided; }

    /**
     * updates the entities position
     */
    public void update() {
		checkAllCollisions();
        xPos += xSpeed;
        yPos += ySpeed;

		Game game = Game.getInstance();
        float screenWidth = game.getScreenWidth();
        float screenHeight = game.getScreenHeight();

        xPos = (xPos < 0) ? (screenWidth - width) : (xPos);
        xPos = (xPos + width > screenWidth) ? (0) : (xPos);

        yPos = (yPos < 0) ? (screenHeight - height) : (yPos);
        yPos = (yPos + height > screenHeight) ? (0) : (yPos);

		if(collided) {
			die();
		}
		collided = false;
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
