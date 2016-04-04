package entity;

import game.Game;
import logger.Log;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Asteroid extends Entity {

    private AsteroidSize size;


    public Asteroid(AsteroidSize size) {
        Game game = Game.getInstance();
        try {
            switch(size) {
                case SMALL:
                    sprite = ImageIO.read(getClass().getResource("/graphics/Asteroids_3.png"));
                    break;
                case MEDIUM:
                    sprite = ImageIO.read(getClass().getResource("/graphics/Asteroids_2.png"));
                    break;
                case BIG:
                    sprite = ImageIO.read(getClass().getResource("/graphics/Asteroids_1.png"));
                    break;
            }
        } catch (IOException e) {
            Log.warn(e.getMessage());
        }
        //TODO cleanup this bad engineering

        this.size = size;
        double plusOrMinusX = (game.randomBool()) ? (1) : (-1);
        double plusOrMinusY = (game.randomBool()) ? (1) : (-1);

        switch (size) {
            case BIG:
                plusOrMinusX *= 1;
                plusOrMinusY *= 1;
                break;
            case MEDIUM:
            case SMALL:
                plusOrMinusX *= 1.33;
                plusOrMinusY *= 1.33;
                break;
        }

        int xVel = (int)(plusOrMinusX*game.randomInRange(1,2));
        int yVel = (int)(plusOrMinusY*game.randomInRange(1,2));

        height = sprite.getHeight();
        width = sprite.getWidth();

        this.setSpeed(xVel, yVel);
    }

    private void split() {
        AsteroidSize newSize = null;
        switch (this.size) {
            case BIG:
                newSize = AsteroidSize.MEDIUM;
                break;
            case MEDIUM:
                newSize = AsteroidSize.SMALL;
                break;
            case SMALL:
                return;
        }

        Asteroid child1 = new Asteroid(newSize);
        Asteroid child2 = new Asteroid(newSize);

        child1.spawnAtParent(this);
        Game.getInstance().addEntity(child1);
        child2.spawnAtParent(this);
        Game.getInstance().addEntity(child2);
    }

    @Override
    public void die() {
        split();
        Game.getInstance().removeEntity(this);
        //play death animation
        //TODO
    }

    public void spawnAtParent(Asteroid parent) {
        Game game = Game.getInstance();
        int xOffset = game.randomInRange(1,3);
        int yOffset = game.randomInRange(1,3);
        xPos = (game.randomBool()) ? (parent.get_x() - xOffset) : (parent.get_x() + xOffset);
        yPos = (game.randomBool()) ? (parent.get_y() - yOffset) : (parent.get_y() + yOffset);
    }
}

