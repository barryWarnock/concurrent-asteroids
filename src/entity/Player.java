package entity;

import gui.Screen;
import logger.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import game.Game;

import static java.awt.event.KeyEvent.*;

public class Player extends Entity {

	private static Player player = new Player();

	private int degOfRotation = 0;
	//0 degrees corresponds to facing directly vertical

	private double linearSpeedChange = 0.25;
	//speed increase in direction player is moving
	private BufferedImage movingSprite;
	private BufferedImage staticSprite;
    private BufferedImage invSprite;

    private final int iFrames = 100;
    private int iFrameAge = 0;


	private Player() {
		try {
			staticSprite = ImageIO.read(getClass().getResource("/graphics/Player.png"));
			width = staticSprite.getWidth();
			height = staticSprite.getHeight();
			movingSprite = ImageIO.read(getClass().getResource("/graphics/Player_move.png"));
            invSprite = ImageIO.read(getClass().getResource("/graphics/Player_inv.png"));

		} catch (IOException e) {
			Log.warn(e.getMessage());
		}
		//TODO cleanup this bad engineering
		xPos = Screen.getInstance().getWidth()/2;
		yPos = Screen.getInstance().getHeight()/2;



	}

	private boolean accelerating = false;
	private boolean rotatingCW = false;
	private boolean rotatingCCW = false;

	//TODO this
	@Override
	public void update() {

		if(accelerating) {
			int theta = degOfRotation % 360;
			xSpeed += (Math.cos(Math.toRadians(theta - 90)) * linearSpeedChange);
			ySpeed += (Math.sin(Math.toRadians(theta - 90)) * linearSpeedChange);
			sprite = movingSprite;

			/*
			move forward, decompose linear speed change into x and y components
			based on the coordinate system where 0 degrees is viewed as vertical
			to the user and x and y axes are swapped relative to Cartesian coordinates
			*/
		} else {
			sprite = staticSprite;
		}
		if(rotatingCCW) {
			degOfRotation -= 3;
		}
		if(rotatingCW) {
			degOfRotation += 3;
		}

		super.update();

		double decayRate = 0.95;
		if(0 != xSpeed) {
			xSpeed *= decayRate;
		}
		if(0 != ySpeed) {
			ySpeed *= decayRate;
		}


        if (iFrameAge < iFrames) {
            iFrameAge++;
            sprite = invSprite;
        }
        else {
            isInvincible = false;
            System.out.println("I am not invincible.");
        }



		sprite = rotate(sprite);

	}

	private BufferedImage rotate(BufferedImage image) {

		BufferedImage newImg = new BufferedImage(image.getWidth(),
									image.getHeight(), image.getType());
		Graphics2D g2d = newImg.createGraphics();


		AffineTransform at = new AffineTransform();

		// 4. translate it to the center of the component
		at.translate(sprite.getWidth() / 2, sprite.getHeight() / 2);

		// 3. do the actual rotation
		at.rotate(Math.toRadians(degOfRotation));

		// 1. translate the object so that you rotate it around the
		//    center (easier :))
		at.translate(-image.getWidth()/2, -image.getHeight()/2);

		// draw the image
		g2d.drawImage(image, at, null);

		return newImg;
	}

	@Override
	public void die() {

		initialize(Screen.getInstance().getWidth()/2,Screen.getInstance().getHeight()/2);
		Game.getInstance().loseLife();

	}

	public static Player getInstance() {
		return player;
	}

	public Player initialize(int xLocation, int yLocation) {
        isInvincible = true;
        System.out.println("I am invincible");
        iFrameAge = 0;
		this.xPos = xLocation;
		this.yPos = yLocation;
		return player;
	}

	public void keyPressed(KeyEvent e) {

		int keyPressed = e.getKeyCode();

		switch(keyPressed) {
			case VK_A:
				rotatingCCW = true;
				//rotate cclockwise, positive degree increase
				break;
			case VK_D:
				rotatingCW = true;
				//rotate clockwise, negative degree increase
				break;
			case VK_W:
				accelerating = true;
				break;
			case VK_SPACE:
				shoot();
				break;
			default:
				break;
		}
	}
	public void keyReleased(KeyEvent e) {

		int keyReleased = e.getKeyCode();

		switch(keyReleased) {
			case VK_A:
				rotatingCCW = false;
				//rotate cclockwise, positive degree increase
				break;
			case VK_D:
				rotatingCW = false;
				//rotate clockwise, negative degree increase
				break;
			case VK_W:
				accelerating = false;
				break;
			default:
				break;
		}
	}

	/**
	 * Fires a bullet from the player.
	 */
	private void shoot() {
		if (Bullet.getCurrentBullets() != Bullet.getMaxBullets()) {
			new Bullet(
					(Math.cos(Math.toRadians(degOfRotation - 90))),
					(Math.sin(Math.toRadians(degOfRotation - 90))),
					xPos+18, yPos).setPlayerSpawned(true);
		}
	}

	@Override
	public void checkCollision2(Entity other) {
		if(other.getClass() != Bullet.class) {
			super.checkCollision2(other);
		}
		else if(((Bullet)other).getPlayerSpawned() == false){
			super.checkCollision2(other);
		}
		else return;
	}
}
