package gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import entity.Player;
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
        int fontSize = 20;
        String lines[] = str.split("\\n");
        for(int i=0; lines.length > i; i++) {
            Graphics backBufferGraphics = backBuffer.getGraphics();
            backBufferGraphics.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
            backBufferGraphics.setColor(Color.white);
            backBufferGraphics.drawString(lines[i], 5, fontSize*(i+1));
        }

    }

    /**
     * Draws an image to the buffer
     * @param img image to print.
     * @param x coordinate
     * @param y coordinate
     */
    public void drawImage(Image img, int x, int y) {
        Graphics backBufferGraphics = backBuffer.getGraphics();
        backBufferGraphics.drawImage(img, y, x, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backBuffer, 0, 0, this);
        backBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    /**
     * generic constructor with a keyListener.
     */
    private Screen() {
        //TODO this is poor engineering
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                Player.getInstance().keyReleased(e);
            }
            @Override
            public void keyPressed(KeyEvent e) {
                Player.getInstance().keyPressed(e);
            }
        });
        setFocusable(true);
    }

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