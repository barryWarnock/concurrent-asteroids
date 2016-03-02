package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Score;

/**
 * This class is a singleton instance of the main screen for displaying graphics.
 * 
 * @author bolster
 *
 */
public class Screen extends JPanel {

	private static Screen screen = new Screen();
	
	private int xDimension;
	private int yDimension;
	
	private int score;
	
	public static void updateScore(int points)
	{
		score += points;
	}
	
//	private BufferedImage backgroundImage = ImageIO.read(getClass().getResource("/images/background.png"));
//	
//	@Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        //background drawing
//        g.drawImage(backgroundImage, 0, 0, null);
//        //player drawing
//        int correctedHeight = HEIGHT - mainPlayer.getYPos() - mainPlayer.HEIGHT;
//        g.drawImage(mainPlayer.characterImage, mainPlayer.X_POS, correctedHeight, null);
//        //food drawing 
//        for(int i=0; i<allFood.length; i++)
//        {
//        	if(allFood[i]!=null){
//        		int correctedFoodHeight= HEIGHT - allFood[i].Y_POS - allFood[i].HEIGHT;
//        		g.drawImage(allFood[i].foodImage, allFood[i].X_Pos, correctedFoodHeight, null);
//        	}
//        }
//        //score and multiplier drawing 
//        g.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
//        g.drawString(Score.getScore(), 625, 20);
//        g.drawString(Score.getMultiplier(), 625, 40);
//        
//        //spikes drawing 
//        for(int i=0; i<allSpikes.length; i++)
//        {
//        	if(allSpikes[i]!=null){
//        		int correctedSpikeHeight= HEIGHT - allSpikes[i].Y_POS - allSpikes[i].HEIGHT;
//        		g.drawImage(allSpikes[i].spikesImage , allSpikes[i].X_Pos, correctedSpikeHeight, null);
//        	}
//        } 
//	}
	
	private Screen() {}
	
	public Screen getInstance() {
		return screen;
	}
	
	/**
	 * Sets the screen to a certain size.
	 */
	public void intialize(int xDim, int yDim) {
		
	}
	
}
