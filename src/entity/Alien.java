package entity;

import game.Game;
import logger.Log;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Alien extends Entity {

    private static boolean currentBullet = false;


    public static void setCurrentBullet(boolean bool){
        currentBullet = bool;
    }

    public Alien(){
        Game game = Game.getInstance();
        try {
            sprite = ImageIO.read(getClass().getResource("/graphics/Aliens.png"));
            width = sprite.getWidth();
            height = sprite.getHeight();
        } catch (IOException e) {
            Log.warn(e.getMessage());
        }

        //Aliens will always start on the left and move to the right (for now)
        xPos = 0;
        yPos = game.randomInRange(0,game.getScreenHeight());

        double plusOrMinusX = 1; //(game.randomBool()) ? (1) : (-1);
        double plusOrMinusY = (game.randomBool()) ? (1) : (-1);

        xSpeed = plusOrMinusX*game.randomInRange(1,2);
        ySpeed = plusOrMinusY*game.randomInRange(1,2);
    }


    @Override
    public void update(){
        if (!currentBullet){
            shoot();
            currentBullet=true;
        }
        super.update();
    }

    private void shoot(){
        new Bullet(
                (Game.getInstance().randomInRange(1,2)),
                (Game.getInstance().randomInRange(1,2)),
                xPos+18, yPos).setPlayerSpawned(false);

    }


    @Override
    public void checkCollision2(Entity other) {
        if(other.getClass() != Bullet.class) {
            super.checkCollision2(other);
        }
        else if(((Bullet)other).getPlayerSpawned() == true){
            super.checkCollision2(other);
        }
        else return;
    }




    //TODO this
    @Override
    public void die(){
        Game game = Game.getInstance();
        game.removeEntity(this);
        //TODO play death animation
    }

}
