package gui;

import java.awt.*;

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

    public Graphics g;

    public void test() {

        //score drawing
        int fontSize = 20;
        g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
        g.setColor(Color.black);
        g.drawString(Integer.toString(500), 5, fontSize);

    }

    @Override
    public void paintComponent(Graphics g) {
        Log.debug("Painting to Buffer");
        super.paintComponent(g);
        this.g = g;
        Log.debug("Done printing to Buffer");
    }
	
	private Screen() {}
	
	public static Screen getInstance() {
		return screen;
	}
	
	/**
	 * Sets the screen to a certain size.
	 */
	public Screen intialize(int width, int height) {
        setSize(width, height);
        return screen;
    }

}