package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import logger.Log;


/**
 * This class is a singleton instance of the main screen for displaying graphics.
 * 
 * @author bolster
 *
 */
public class Screen extends JPanel {

	private static Screen screen = new Screen();

    private BufferedImage backBuffer;

    /**
     * Draws a string to the top left of a screen.
     * This occurs on the buffer.
     * @param str The game score.
     */
    public void drawText(String str) {
        Log.debug("Drawing a string");
        int fontSize = 20;
        Graphics backBufferGraphics = backBuffer.getGraphics();
        backBufferGraphics.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
        backBufferGraphics.setColor(Color.white);
        backBufferGraphics.drawString(str, 5, fontSize);
    }

    /**
     * Draws an image to the buffer
     * @param img image to print.
     * @param x coordinate
     * @param y coordinate
     */
    public void drawImage(Image img, int x, int y) {
        Graphics backBufferGraphics = backBuffer.getGraphics();
        backBufferGraphics.drawImage(img, x, y, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Log.debug("Painting to Buffer");
        super.paintComponent(g);
        g.drawImage(backBuffer, 0, 0, this);
        Log.debug("Done painting to Buffer");
        backBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    /**
     * generic constructor.
     */
    private Screen() { }

    /**
     *
     * @return The Screen
     */
	public static Screen getInstance() {
		return screen;
	}
	
	/**
	 * Sets the screen to a certain size.
	 */
	public Screen initialize(int width, int height) {
        setSize(width, height);
        backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        return screen;
    }

}