package entity;

import game.Game;
import main.Main;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Bullet extends Entity{

    private int age;

    private final int bulletLife=50;
    private final int bulletSpeed=7;
    private static final int maxBullets=8;
    private static int currentBullets=0;

    public static int getMaxBullets(){
        return maxBullets;
    }

    public static int getCurrentBullets(){
        return currentBullets;
    }

    public static void fireBullet(){
        currentBullets++;
    }

    public Bullet(double xSpeed, double ySpeed, double xPos, double yPos) {
        Game.getInstance().addEntity(this);
        this.ySpeed = ySpeed*bulletSpeed;
        this.xSpeed = xSpeed*bulletSpeed;
        this.xPos = xPos;
        this.yPos = yPos;
        try {
            sprite = ImageIO.read(getClass().getResource("/graphics/Bullet.png"));
            width = sprite.getWidth();
            height = sprite.getHeight();
        } catch (IOException e) {
            logger.Log.warn(e.getMessage());
        }

    }

    @Override
    public void update() {
        age++;
        if(bulletLife < age) {
            die();
        }
        super.update();
    }

    @Override
    public void die() {

        Game game = Game.getInstance();
        game.removeEntity(this);
        currentBullets--;

    }
}
