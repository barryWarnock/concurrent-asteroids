package entity;

import game.Game;

import java.awt.*;

enum AsteroidSize {
    BIG,
    MEDIUM,
    SMALL
}

public class Asteroid extends Entity {

    private AsteroidSize size;

    public Asteroid(AsteroidSize size) {
        Game game = Game.getInstance();

        this.size = size;
        int plusOrMinusX = (game.randomBool()) ? (1) : (-1);
        int plusOrMinusY = (game.randomBool()) ? (1) : (-1);

        switch (size) {
            case BIG:
                plusOrMinusX *= 1;
                plusOrMinusY *= 1;
                break;
            case MEDIUM:
                plusOrMinusX *= 2;
                plusOrMinusY *= 2;
                break;
            case SMALL:
                plusOrMinusX *= 3;
                plusOrMinusY *= 3;
                break;
        }

        int xVel = plusOrMinusX*game.randomInRange(1,10);
        int yVel = plusOrMinusY*game.randomInRange(1,10);


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

        Game game = Game.getInstance();
        Asteroid child1 = new Asteroid(newSize);
        Asteroid child2 = new Asteroid(newSize);

        child1.spawnAtParent(this);
        child2.spawnAtParent(this);
    }

    @Override
    public void die() {
        Game game = Game.getInstance();
        split();
        game.removeEntity(this);
        //play death animation
    }

    public void spawnAtParent(Asteroid parent) {
        Game game = Game.getInstance();
        int xOffset = game.randomInRange(1,5);
        int yOffset = game.randomInRange(1,5);
        xPos = (game.randomBool()) ? (parent.get_x() - xOffset) : (parent.get_x() + xOffset);
        yPos = (game.randomBool()) ? (parent.get_y() - yOffset) : (parent.get_y() + yOffset);
    }

    @Override
    public void draw(Graphics buffer) {

    }
}

