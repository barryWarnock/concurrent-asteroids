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
        width = sprite.getWidth();
        height = sprite.getHeight();

        //Aliens will always start on the left and move to the right (for now)
        xPos = 0;
        yPos = game.randomInRange(0,game.getScreenHeight());

        double plusOrMinusX = 1; //(game.randomBool()) ? (1) : (-1);
        double plusOrMinusY = (game.randomBool()) ? (1) : (-1);

        xSpeed = plusOrMinusX*game.randomInRange(1,2);
        ySpeed = plusOrMinusY*game.randomInRange(1,2);
    }










    //TODO this
    @Override
    public void die(){
        Game game = Game.getInstance();
        game.removeEntity(this);
        //TODO play death animation
    }

}
