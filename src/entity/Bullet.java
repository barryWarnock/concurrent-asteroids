package entity;

import game.Game;
import main.Main;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Bullet extends Entity{

    private int age;

    private final int bulletLife=100;
    private final int bulletSpeed=8;

    public Bullet(double xSpeed, double ySpeed, double xPos, double yPos) {
        Game.getInstance().addEntity(this);
        this.ySpeed = ySpeed*bulletSpeed;
        this.xSpeed = xSpeed*bulletSpeed;
        this.xPos = xPos;
        this.yPos = yPos;
        try {
            sprite = ImageIO.read(getClass().getResource("/graphics/Bullet.png"));
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

    //if this is active, it crashes with a ConcurrentModificationException
    public void die() {

        Game game = Game.getInstance();
        game.removeEntity(this);

    }
}
