package entity;

import gui.Screen;
import logger.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.awt.event.KeyEvent.*;

public class Player extends Entity {

	private static Player player = new Player();

	private int degOfRotation = 0;
	//0 degrees corresponds to facing directly vertical

	private double linearSpeedChange = 0.25;
	//speed increase in direction player is moving
	private BufferedImage movingSprite;
	private BufferedImage staticSprite;

	private Player() {
		try {
			staticSprite = ImageIO.read(getClass().getResource("/graphics/Player.png"));
			movingSprite = ImageIO.read(getClass().getResource("/graphics/Player_move.png"));
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
			xSpeed += (Math.sin(Math.toRadians(theta - 90)) * linearSpeedChange);
			ySpeed += (Math.cos(Math.toRadians(theta - 90)) * linearSpeedChange);
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

		xPos += xSpeed;
		yPos += ySpeed;

		double decayRate = 0.95;
		if(0 != xSpeed) {
			xSpeed *= decayRate;
		}
		if(0 != ySpeed) {
			ySpeed *= decayRate;
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
		//TODO this
	}

	public static Player getInstance() {
		return player;
	}

	public Player initialize(int xLocation, int yLocation) {
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
				Log.debug("Player pressing A");
				break;
			case VK_D:
				rotatingCW = true;
				//rotate clockwise, negative degree increase
				Log.debug("Player pressing D");
				break;
			case VK_W:
				accelerating = true;
				Log.debug("Player pressing W");
				break;
			case VK_SPACE:
				shoot();
				Log.debug("Player pressing Space");
				break;
			default:
				Log.debug("Player pressing: " + keyPressed);
				break;
		}
	}
	public void keyReleased(KeyEvent e) {

		int keyReleased = e.getKeyCode();

		switch(keyReleased) {
			case VK_A:
				rotatingCCW = false;
				//rotate cclockwise, positive degree increase
				Log.debug("Player released A");
				break;
			case VK_D:
				rotatingCW = false;
				//rotate clockwise, negative degree increase
				Log.debug("Player released D");
				break;
			case VK_W:
				accelerating = false;
				Log.debug("Player releaded W");
				break;
			default:
				Log.debug("Player releasing: " + keyReleased);
				break;
		}
	}

	/**
	 * Fires a bullet from the player.
	 */
	private void shoot() {
		new Bullet((Math.sin(Math.toRadians(degOfRotation - 90))),
					(Math.cos(Math.toRadians(degOfRotation - 90))),
					xPos+20, yPos+18);
	}
}
