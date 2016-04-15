package entity;

import game.Game;
import logger.Log;
import main.Main;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Asteroid extends Entity {

    private AsteroidSize size;
    private int asteroidScore = 0;


    public Asteroid(AsteroidSize size) {
        Game game = Game.getInstance();
        try {
            switch(size) {
                case SMALL:
                    sprite = ImageIO.read(getClass().getResource("/graphics/Asteroids_3.png"));
                    asteroidScore = 400;
                    break;
                case MEDIUM:
                    sprite = ImageIO.read(getClass().getResource("/graphics/Asteroids_2.png"));
                    asteroidScore = 200;
                    break;
                case BIG:
                    sprite = ImageIO.read(getClass().getResource("/graphics/Asteroids_1.png"));
                    asteroidScore = 100;
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
                plusOrMinusX *= .5;
                plusOrMinusY *= .5;
                break;
            case MEDIUM:
                plusOrMinusX *= .6;
                plusOrMinusY *= .6;
                break;
            case SMALL:
                plusOrMinusX *= .7;
                plusOrMinusY *= .7;
                break;
        }

        xSpeed = (plusOrMinusX*game.randomInRange(1,2));
        ySpeed = (plusOrMinusY*game.randomInRange(1,2));

        height = sprite.getHeight();
        width = sprite.getWidth();
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
        if(!Main.testMode) {
            split();
        }
        Game.getInstance().removeEntity(this);
        Game.getInstance().incrementScore(asteroidScore);
//        System.out.println("Incrementing score by: "+asteroidScore);
        collisions = null;
        //play death animation
        //TODO this
    }

    public void spawnAtParent(Asteroid parent) {
        xPos = parent.get_x();
        yPos = parent.get_y();
    }
}

