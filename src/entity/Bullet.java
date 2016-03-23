package entity;

import game.Game;
import main.Main;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Bullet extends Entity{

    private int age;

    public Bullet(double xSpeed, double ySpeed, double xPos, double yPos) {
        Game.getInstance().addEntity(this);
        this.ySpeed = ySpeed;
        this.xSpeed = xSpeed;
        try {
            sprite = ImageIO.read(getClass().getResource("/graphics/Bullet.png"));
        } catch (IOException e) {
            logger.Log.warn(e.getMessage());
        }

    }

    @Override
    public void update() {
        xPos += xSpeed;
        yPos += ySpeed;
        age++;
        if(Main.bulletLife < age) {
            die();
        }
    }

    @Override
    public void die() {
        //TODO implement this
    }
}
