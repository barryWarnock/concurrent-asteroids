package entity;

import game.Game;
import gui.Screen;
import logger.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Alien extends Entity {

    private BufferedImage sprite;


    public Alien(){
        Game game = Game.getInstance();
        try {
            sprite = ImageIO.read(getClass().getResource("/graphics/Aliens.png"));
        } catch (IOException e) {
            Log.warn(e.getMessage());
        }
        xPos = Screen.getInstance().getWidth()/2;
        yPos = Screen.getInstance().getHeight()/2;
    }










    //TODO this
    @Override
    public void die(){
        Game game = Game.getInstance();
        game.removeEntity(this);
        //TODO play death animation
    }

}
